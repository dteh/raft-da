package message;

public class LogEntry extends RaftMessage{
	public LogEntry(Object obj){
		name = "LogEntry";
		payload = obj;
	}
}
