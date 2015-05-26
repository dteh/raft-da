package message;

import raft.RaftNode;

public class ResponseLeader extends RaftMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2887471574751867764L;
	public int term;
	public ResponseLeader(org.jgroups.Address LeaderAddress){
		name = "ResponseLeader";
		payload = LeaderAddress;
		term = RaftNode.currentTerm;
	}
}
