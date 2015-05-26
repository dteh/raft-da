package message;

public class RequestLeader extends RaftMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3224450195860125174L;

	public RequestLeader(){
		name = "RequestLeader";
	}
}
