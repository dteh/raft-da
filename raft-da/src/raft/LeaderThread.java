package raft;

import message.AppendEntries;

/**
 * This thread constantly checks if a new state to push is available,
 * if not, sends a heartbeat
 */
public class LeaderThread implements Runnable{
	/**
	 * Checks whether a new state is available to send, and broadcasts it
	 * If not, sends empty AppendEntries (as heartbeat)
	 * @param obj - object to push to nodes
	 */
	public void run(){
		System.out.println("Leader Thread started");
		while(RaftNode.state.State.equals("Leader")){
			try{
				Thread.sleep(80);
				pushAppendEntries();
			}catch(InterruptedException e){}
		}
	}
	
	public void pushAppendEntries(){
		if(RaftNode.getNewStateAvailable()){
			RaftNode.setNewStateAvailable(false);
			try{
				SetChannel.channel.send(null, new AppendEntries(RaftNode.getStateObject()));
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
