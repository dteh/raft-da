package state;

public abstract class State {
	abstract void on_AppendEntries(); //only definable in follower?
	abstract void on_LeaderTimeOut(); //probably can be defined here
	abstract void on_VoteRequest(); //maybe this can be defined here
	abstract void on_VoteReceived(); //move this to candidate?
	abstract void on_UserRequest(); //probably followers
	abstract void heartBeat(); //move this to leader
	abstract void on_heartBeat(); //all except leader
	
	
	void randomTimeOut(){
		//generate random timeout for leader election
	}
	
}
