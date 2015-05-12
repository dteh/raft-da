package state;

import raft.RaftNode;

public class Candidate extends State {
	public Candidate(){
		State = "Candidate";
		voteCount = 0;
		RaftNode.setTimeoutVar(false);
	}
	// Define how a candidate should be have & methods involved
	
	/**
	 * Sends leadership request with current term to all nodes
	 */
	public void sendVoteRequest(){
		try{
			raft.SetChannel.channel.send(null,new message.RequestVote(raft.RaftNode.currentTerm));
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
