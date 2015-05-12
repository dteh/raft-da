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
		int nextTimeOut = new Random().nextInt(150) + 150;
		RaftNode.limit = System.currentTimeMillis() + nextTimeOut;
		return nextTimeOut;
	}
	
	/**
	 * Continuously checks class variable limit to see whether 
	 * the system time is after it
	 * @param timeOut
	 */
	void timerCountDown(){
		while(RaftNode.getTimeoutVar()){
			if(System.currentTimeMillis() >= RaftNode.limit && RaftNode.getTimeoutVar()){
				raft.RaftNode.onTimeOut();
			}
		}
	}
}
