package state;

public class Candidate extends State {
	public Candidate(){
		State = "candidate";
	}
	// Define how a candidate should be have & methods involved
	public void sendVoteRequest(){
		try{
			raft.SetChannel.channel.send(null,new message.RequestVote(raft.RaftNode.currentTerm));
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
