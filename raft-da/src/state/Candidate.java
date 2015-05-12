package state;

public class Candidate extends State {
	public Candidate(){
		State = "Candidate";
		voteCount = 0;
	}
	// Define how a candidate should be have & methods involved
	
	/**
	 * Sends leadership request with current term to all ndoes
	 */
	public void sendVoteRequest(){
		try{
			raft.SetChannel.channel.send(null,new message.RequestVote(raft.RaftNode.currentTerm));
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
