package state;
import raft.RaftNode;
import raft.TimeoutThread;

public class Follower extends State{
	public Follower(){
		State = "Follower";
		Thread timeoutThread = new Thread(new TimeoutThread());
		RaftNode.setTimeoutVar(true);
		
		timeoutThread.start();
	}
}
