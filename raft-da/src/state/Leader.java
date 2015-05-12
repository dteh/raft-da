package state;

import raft.RaftNode;
import raft.SetChannel;
import message.AppendEntries;

public class Leader extends State{
	public Leader(){
		State = "Leader";
		RaftNode.setTimeoutVar(false);
	}
	
	/**
	 * Checks whether a new state is available to send, and broadcasts it
	 * If not, sends empty AppendEntries (as heartbeat)
	 * @param obj - object to push to nodes
	 */
	public void pushAppendEntries(Object obj){
		if(RaftNode.getNewStateAvailable()){
			RaftNode.setNewStateAvailable(false);
			try{
				SetChannel.channel.send(null, new AppendEntries(obj));
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			try{
				SetChannel.channel.send(null,new AppendEntries(null));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
