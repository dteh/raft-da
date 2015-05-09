package raft;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.util.Util;

import state.State;


public class RaftNode {
	static long nextTimeOut;
	static State state;
	static org.jgroups.Address LEADER;
	SetChannel addresses;

	
	public static void onTimeOut(){
		state = new state.Candidate();
	}
	
	/**
	 * Startup process:
	 * 		- Get available nodes
	 * 		- Broadcast a request for the leader
	 */
	private void init() throws Exception{
		addresses = new SetChannel();
		new Thread(addresses).start();
		Thread.sleep(500);
		LEADER = getLeader();
	}
	
	/**
	 * Broadcasts a request for the Leader's ID
	 */
	public static org.jgroups.Address getLeader() throws Exception{
		org.jgroups.Address clusterLeader = null;
		JChannel c = SetChannel.channel;
		
		// Figuring out who is the leader
		while(true){
			// Member list hasn't loaded yet
			if(SetChannel.members == null){
				System.out.println("Waiting for cluster..");
				Thread.sleep(200);
			// Only one member in cluster - make me the leader
			}else if(SetChannel.members.size() == 1){
				return SetChannel.members.get(0);
			}
			// Multiple members, send out a leader request
			else{
				byte[] buf = Util.objectToByteBuffer(new message.RequestLeader());
				Message msg = new Message(null,null,buf);
				c.send(msg);
				break;
			}
		}
		return clusterLeader;
	}
	
	public static void main(String[] args) throws Exception{
		System.setProperty("java.net.preferIPv4Stack" , "true");
		// uncomment and force address if not working
		//System.setProperty("jgroups.bind_addr" , "192.168.2.17");
		LEADER = null;
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
