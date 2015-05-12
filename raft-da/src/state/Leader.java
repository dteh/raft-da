package state;

import raft.RaftNode;


public class Leader extends State{
	public Leader(){
		State = "Leader";
		RaftNode.setTimeoutVar(false);
	}
	// define how a leader should behave
}
