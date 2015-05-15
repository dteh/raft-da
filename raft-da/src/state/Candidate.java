package state;

import raft.RaftNode;
import raft.TimeoutThread;

public class Candidate extends State {
	public Candidate(){
		State = "Candidate";
		voteCount = 0;
		RaftNode.setTimeoutVar(true);
		System.out.println("Sou Candidate");
		//Thread timeoutThread = new Thread(new TimeoutThread());
		//timeoutThread.start();
	}
	
	/**
	 * Sends leadership request with current term to all nodes
	 */
	public void sendVoteRequest(){
		try{
			raft.SetChannel.channel.send(null,new message.RequestVote(raft.RaftNode.currentTerm));
			System.out.println(raft.RaftNode.currentTerm + " current term");
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
