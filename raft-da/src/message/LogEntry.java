package message;

public class LogEntry extends RaftMessage{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4163239260891624375L;

	public LogEntry(Object obj){
		name = "LogEntry";
		payload = obj;
	}
}
