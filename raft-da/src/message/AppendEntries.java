package message;

public class AppendEntries extends RaftMessage{
	public AppendEntries(Object stateObject){
		name = "AppendEntries";
		payload = stateObject;
	}
}