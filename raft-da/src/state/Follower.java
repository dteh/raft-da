package state;

import raft.RaftNode;
import raft.TimeoutThread;

public class Follower extends State{
	public Follower(){
		State = "Follower";
		RaftNode.setTimeoutVar(true);
		System.out.println("Sou Follower");

		Thread timeoutThread = new Thread(new TimeoutThread());
		timeoutThread.start();
	}
}
