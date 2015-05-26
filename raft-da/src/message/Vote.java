package message;

/**
 * Receiving this message indicates that the receiving node has been voted for
 * in the term it requested
 */
public class Vote extends RaftMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7391097585208069091L;

	public Vote(int term){
		name = "Vote";
		payload = term;
	}
}
