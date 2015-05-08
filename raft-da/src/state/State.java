package state;
import java.util.Random;

public abstract class State {
	String State;
	//Runs the instructions in the received message
//	abstract void AppendEntries(); //only definable in follower
	
	//Changes state to candidate state
	
	//??
//	abstract void on_VoteRequest(); //maybe this can be defined here
	
	//Increments vote counter (candidate only though)
//	abstract void on_VoteReceived(); //move this to candidate?
	
//	abstract void on_UserRequest(); //probably followers	
	
	int nextRandomTimeOut(){
		int nextTimeOut = new Random().nextInt(150) + 150;
		return nextTimeOut;
	}
	
	void timerCountDown(int timeOut){
		long limit = System.currentTimeMillis() + timeOut;
		long current = System.currentTimeMillis();

		while(current < limit){
			current = System.currentTimeMillis();
		}
		if(current >= limit){
			raft.RaftNode.onTimeOut();
		}
	}
}
