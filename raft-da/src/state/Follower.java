package state;

public abstract class Follower extends State{
	public Follower(){
		State = "follower";
	}
	
	public String AppendEntries(String m){
		if(m.equals("")){
			nextRandomTimeOut();
		}
		else{
			return m; //Return message to the program implementing library
		}
		return "";
	}
}
