package raft;
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
		if(m.name == "RequestLeader"){
			// If the node knows the leader
			if(RaftNode.LEADER != null){
				// Send back to the source of the msg, the leader value
				SetChannel.channel.send(msg.getSrc(),new message.ResponseLeader(RaftNode.LEADER));
			}
		}
		if(m.name == "ResponseLeader"){
			RaftNode.LEADER = (Address)m.payload;
		}
		if(m.name == "RequestVote"){
			// do something
		}
		if(m.name == "AppendEntries"){
			// do something
		}
		if(m.name == "Vote"){
			//  Add a vote to tally if received
			RaftNode.state.voteCount += 1;
			
			// If quorum is reached, broadcast that you are the leader
			if(RaftNode.state.voteCount >= (SetChannel.members.size()/2)+1){
				SetChannel.channel.send(null,new message.ResponseLeader(SetChannel.channel.getAddress()));
			}
		}
		
	}
	
	/**
	 * If the node has a value for the leader, it will respond to the requester with
	 * its value
	 * @param requester - address to respond to
	 */
	private void ResponseLeader(Address requester){
		if(RaftNode.LEADER != null){
			try{
				byte[] buf = Util.objectToByteBuffer(new message.ResponseLeader(RaftNode.LEADER));
				Message m = new Message(requester,buf);
				SetChannel.channel.send(m);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
