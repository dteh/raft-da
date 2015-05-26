package state;

import raft.RaftNode;

public class Leader extends State{
	public Leader() throws Exception{
		State = "Leader";
		RaftNode.setTimeoutVar(false);
	}
}
