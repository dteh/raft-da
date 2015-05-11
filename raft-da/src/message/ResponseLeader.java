package message;

import raft.RaftNode;

public class ResponseLeader extends RaftMessage{
	public int term;
	public ResponseLeader(org.jgroups.Address LeaderAddress){
		name = "ResponseLeader";
		payload = LeaderAddress;
		term = RaftNode.currentTerm;
	}
}
