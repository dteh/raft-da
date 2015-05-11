package raft;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgroups.JChannel;
import org.jgroups.Address;
import org.jgroups.Message;
import org.jgroups.util.Util;

import state.Candidate;
import state.State;


public class RaftNode {
	static long nextTimeOut;
	static State state;
	static org.jgroups.Address LEADER;
	public static int currentTerm;
	public static boolean broadcastLeaderThisTerm;
	public static Map<Integer, Boolean> voteLog;
	SetChannel addresses;

	/**
	 * (c) Peter Lawrey (StackOverflow)
	 * 
	 * Create new structure for voting only once per term.
	 * Term numbers are mapped to a boolean for whether the node has voted
	 * for that particular term.
	 * Size is limited to maxEntries (to prevent large data use after a long time)
	 * @param maxEntries
	 * @return
	 */
	public static <Integer, Boolean> Map<Integer, Boolean> createLRUMap(final int maxEntries) {
	    return new LinkedHashMap<Integer, Boolean>(maxEntries*10/7, 0.7f, true) {
	        @Override
	        protected boolean removeEldestEntry(Map.Entry<Integer, Boolean> eldest) {
	            return size() > maxEntries;
	        }
	    };
	}
	
	
	/**
	 * Startup process:
	 * 		- Get available nodes
	 * 		- Broadcast a request for the leader
	 */
	private void init() throws Exception{
		voteLog = createLRUMap(20);
		currentTerm = 0;
		addresses = new SetChannel();
		new Thread(addresses).start();
		Thread.sleep(500);
		getLeader();
	}
	
	/**
	 * Broadcasts a request for the Leader's ID
	 */
	public static void getLeader() throws Exception{
		JChannel c = SetChannel.channel;
		
		// Figuring out who is the leader
		while(true){
			// Member list hasn't loaded yet
			if(SetChannel.members == null){
				System.out.println("Waiting for cluster..");
				Thread.sleep(200);
			// Only one member in cluster - make me the leader
			}else if(SetChannel.members.size() == 1){
				LEADER = SetChannel.members.get(0);
				currentTerm = 1;
			}
			// Multiple members, send out a leader request
			else{
				c.send(null,new message.RequestLeader());
				break;
			}
		}
	}
	
	/**
	 * When timeout is triggered, initiate voting process
	 */
	public static void onTimeOut(){
		state = new state.Candidate();
		currentTerm += 1;
		state.voteCount = 0;
		((Candidate) state).sendVoteRequest();	
	}
	
	public static void main(String[] args) throws Exception{
		System.setProperty("java.net.preferIPv4Stack" , "true");
		// uncomment and force address if not working
		//System.setProperty("jgroups.bind_addr" , "192.168.2.17");
		LEADER = null;
		broadcastLeaderThisTerm = false;
		currentTerm = 0;
		RaftNode start = new RaftNode();
		start.init();
		/*
		 * Prints list of nodes once per second
		 */
		/*while(true){
			Thread.sleep(1000);
			if (SetChannel.members != null){
				System.out.println(SetChannel.members);
			}
		}*/
	}
}
