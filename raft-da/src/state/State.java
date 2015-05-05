package state;

public abstract class State {
	String State;
	//Runs the instructions in the received message
	abstract void AppendEntries(); //only definable in follower
	
	//Changes state to candidate state
	abstract void on_LeaderTimeOut(); //probably can be defined here
	
	//??
	abstract void on_VoteRequest(); //maybe this can be defined here
	
	//Increments vote counter (candidate only though)
	abstract void on_VoteReceived(); //move this to candidate?
	
	//
	abstract void on_UserRequest(); //probably followers
	
	//
	abstract void heartBeat(); //move this to leader
	abstract void on_heartBeat(); //all except leader
	
	
	void nextRandomTimeOut(){
		//generate random timeout for leader election
	}
	
}
