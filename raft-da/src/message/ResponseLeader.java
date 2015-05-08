package message;

public class ResponseLeader extends RaftMessage{
	public ResponseLeader(org.jgroups.Address LeaderAddress){
		name = "ResponseLeader";
		payload = LeaderAddress;
	}
}
