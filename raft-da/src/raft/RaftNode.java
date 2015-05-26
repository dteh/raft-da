package raft;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import raft.InstructionApplier;
import message.AppendEntries;
import state.Candidate;
import state.State;

public class RaftNode {
	static String clusterName;
	static long nextTimeOut;
	public static State state;
	static org.jgroups.Address LEADER;
	public static int currentTerm;
	public static boolean broadcastLeaderThisTerm;
	public static Map<Integer, Boolean> voteLog;
	public static volatile long limit;
	private static boolean runningTimeout;
	SetChannel addresses;
	public static boolean iAmLeader;
	public static String myAddress;
	
	// 
	private static Object logState;
	public static LinkedList<Object> instructionQueue;
	public static InstructionApplier apply;
	private static boolean newStateAvailable;
	
	public RaftNode(){
		LEADER = null;
		broadcastLeaderThisTerm = false;
		currentTerm = 0;
		voteLog = createLRUMap(20);
		currentTerm = 0;
		addresses = new SetChannel();
		runningTimeout = false;
		newStateAvailable = false;
	}
	
	/**
	 * get/set whether a new instruction to be pushed to nodes (from leader)
	 */
	public static void setNewStateAvailable(boolean b){
		newStateAvailable = b;
	}
	public static boolean getNewStateAvailable(){
		return newStateAvailable;
	}
	
	/**
	 * get/set whether the timeout thread should be active
	 */
	public static void setTimeoutVar(boolean v){
		runningTimeout = v;
	}
	public static boolean getTimeoutVar(){
		return runningTimeout;
	}
	
	/**
	 * get/set the object state to propagate to other nodes
	 */
	public static void setStateObject(Object obj){
		logState = obj;
	}
	public static Object getStateObject(){
		return logState;
	}
	
	/**
	 * Propagate the server state.
	 * The state object is set as the server's state object,
	 * then broadcast.
	 * @param stateObject
	 */
	public void sendState(Object stateObject){
		logState = stateObject;
		try{
		SetChannel.channel.send(null, new AppendEntries(logState));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
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
	public static <K, V> Map<K, V> createLRUMap(final int maxEntries) {
	    return new LinkedHashMap<K, V>(maxEntries*10/7, 0.7f, true) {
	        /**
			 * 
			 */
			private static final long serialVersionUID = 8446823426125300926L;

			@Override
	        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
	            return size() > maxEntries;
	        }
	    };
	}
	
	/**
	 * Startup process:
	 * 		- Get available nodes
	 * 		- Broadcast a request for the leader
	 */
	void init(String clustername) throws Exception{
		new Thread(addresses).start();
		clusterName = clustername;
		Thread.sleep(500);
		getLeader();
		myAddress = SetChannel.channel.getAddressAsString();
	}
	
	/**
	 * Broadcasts a request for the Leader's ID
	 */
	public static void getLeader() throws Exception{		
		// Figuring out who is the leader
		while(true){
			// Member list hasn't loaded yet
			if(SetChannel.members == null){
				System.out.println("Waiting for cluster..");
				Thread.sleep(200);
			// Only one member in cluster - make me the leader
			}else if(SetChannel.members.size() == 1){
				iAmLeader = false;
				LEADER = SetChannel.members.get(0);
				currentTerm = 1;
				System.out.println("I AM THE LEADER");
				state = new state.Leader();
				Thread leaderThread = new Thread(new LeaderThread());
				leaderThread.start();
				iAmLeader = true;
				break;
			}
			// Multiple members, send out a leader request
			else{
				RaftNode.state = new state.Follower();
				SetChannel.channel.send(null,new message.RequestLeader());
				break;
			}
		}
	}
	
	/**
	 * 
	 * When timeout is triggered, initiate voting process
	 */
	public static void onTimeOut(){
		state = new state.Candidate();
		currentTerm += 1;
		((Candidate) state).sendVoteRequest();
	}
	
	// XXX: COMMENT THIS OUT WHEN COMPILING AS A LIBRARY (THERE SHOULD BE NO MAIN)
	public static void main(String[] args) throws Exception{
		System.setProperty("java.net.preferIPv4Stack" , "true");
		RaftNode.setStateObject("");
		// uncomment and force address if not working
		//System.setProperty("jgroups.bind_addr" , "192.168.2.17");
		RaftNode start = new RaftNode();
		start.init("raft");
	}
}
