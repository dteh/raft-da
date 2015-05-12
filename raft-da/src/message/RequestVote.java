package message;

/**
 * This message is a request for leadership.
 * @param currentTerm - to allow other nodes to decide if they
 * should vote for this node.
 */
public class RequestVote extends RaftMessage {
	public RequestVote(int currentTerm){
		name = "RequestVote";
		payload = currentTerm;
	}
}
