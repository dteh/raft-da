package message;

/**
 * This message tells the receiver to set their state object to
 * the one inside the message.
 * If the message is only a heartbeat, pass null as the stateObject
 */
public class AppendEntries extends RaftMessage{
	public AppendEntries(Object stateObject){
		name = "AppendEntries";
		payload = stateObject;
	}
}