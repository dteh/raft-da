package state;

import raft.RaftNode;
import raft.SetChannel;
import message.AppendEntries;

public class Leader extends State{
	public Leader() throws Exception{
		State = "Leader";
		RaftNode.setTimeoutVar(false);
	}
}
