package raft;
import message.ResponseLeader;
import org.jgroups.Message;
import org.jgroups.Address;
import org.jgroups.util.*;

public class ReceiveMessage {
	/*
	 * Message parsing
	 */
	public void parse(Message msg) throws Exception{
		message.RaftMessage m = (message.RaftMessage)msg.getObject();
		System.out.println(m.name);
		if(m.name.equals("RequestLeader")){
			// If the node knows the leader
			if(RaftNode.LEADER != null){
				// Send back to the source of the msg, the leader value
				SetChannel.channel.send(msg.getSrc(),new ResponseLeader(RaftNode.LEADER));
			}
		}
		if(m.name.equals("ResponseLeader")){
			RaftNode.LEADER = (Address)m.payload;
			RaftNode.currentTerm = ((ResponseLeader)m).term;
			RaftNode.state = new state.Follower();
		}
		/*
		 *IF message received is a request for leadership:
		 *	- If it is a new term
		 *		- Check to see whether if the term is in the vote log
		 *			-if not, vote and add to log
		 *			-if true, check if value is false
		 *				-if so, vote and change value to true
		 *				-if not, do not vote
		 */
		if(m.name.equals("RequestVote")){
			// !! Important: Watch this.. maybe > needs to be changed to >=
			if((int)m.payload > RaftNode.currentTerm){
				if(RaftNode.voteLog.containsKey((Integer)m.payload)){
					if(RaftNode.voteLog.get((Integer)m.payload) == false){
						//Send vote
						SetChannel.channel.send(msg.getSrc(),new message.Vote((int)m.payload));
					} //Else, don't send a vote
				}else{
					RaftNode.voteLog.put((Integer)m.payload, true);
					//Send vote
					SetChannel.channel.send(msg.getSrc(),new message.Vote((int)m.payload));
				}
			}
		}
		if(m.name.equals("AppendEntries")){
			RaftNode.setStateObject(m.payload);
		}
		if(m.name.equals("Vote")){
			//  Add a vote to tally if received
			if((int)m.payload == RaftNode.currentTerm){
				RaftNode.state.voteCount += 1;
			}
			// If quorum is reached, broadcast that you are the leader
			// The reason the threshold is not 1/2 + 1 because 1/2 assumes the node trying
			// for leadership is voting for itself
			if(RaftNode.state.voteCount >= (SetChannel.members.size()/2)){
				if(RaftNode.broadcastLeaderThisTerm == false){
					SetChannel.channel.send(null,new ResponseLeader(SetChannel.channel.getAddress()));
					RaftNode.broadcastLeaderThisTerm = true;
					System.out.println("I AM THE LEADER");
					RaftNode.state = new state.Leader();
				}
			}
		}
		
	}
}
