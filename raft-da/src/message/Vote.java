package message;

public class Vote extends RaftMessage{
	/**
	 * Receiving this message indicates that the receiving node has been voted for
	 * in the term it requested
	 */
	public Vote(int term){
		name = "Vote";
		payload = term;
	}
}
