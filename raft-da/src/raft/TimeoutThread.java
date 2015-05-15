package raft;

import java.util.Random;

public class TimeoutThread implements Runnable{
	@Override
	public void run(){
		while(RaftNode.getTimeoutVar()){
			timerCountDown();
		}
	}
	
	/**
	 * Generates a new timeout between 150 and 300ms
	 * @return
	 */
	static int nextRandomTimeOut(){
		int nextTimeOut = new Random().nextInt(150) + 200; //FIXME
		RaftNode.limit = System.currentTimeMillis() + nextTimeOut;
		return nextTimeOut;
	}
	
	static int nextRandomTimeOut(int i){
		int nextTimeOut = new Random().nextInt(150) + i;
		RaftNode.limit = System.currentTimeMillis() + nextTimeOut;
		return nextTimeOut;
	}
	
	/**
	 * Continuously checks class variable limit to see whether 
	 * the system time is after it
	 * @param timeOut
	 */
	void timerCountDown(){
		if(RaftNode.getTimeoutVar())
			System.out.println("Timeout Running");
		while(RaftNode.getTimeoutVar()){
			if(System.currentTimeMillis() > RaftNode.limit && RaftNode.getTimeoutVar()){
				System.out.println("TIMED OUT");
				raft.RaftNode.setTimeoutVar(false);
				nextRandomTimeOut();
				raft.RaftNode.onTimeOut();
				break;
			}
		}
	}
}
