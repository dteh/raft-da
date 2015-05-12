package message;

/**
 * Receiving this message indicates that the receiving node has been voted for
 * in the term it requested
 */
public class Vote extends RaftMessage{
	public Vote(int term){
		name = "Vote";
		payload = term;
	}
}
