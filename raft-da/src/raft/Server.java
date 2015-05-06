package raft;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.util.Util;

import state.State;


public class Server {
	static long nextTimeOut;
	static State state;
	static org.jgroups.Address Leader;
	SetChannel addresses;

	
	public static void onTimeOut(){
		state = new state.Candidate();
	}
	
	private void init() throws Exception{
		addresses = new SetChannel();
		new Thread(addresses).start();
		Thread.sleep(500);
		Leader = getLeader();
	}
	
	public static org.jgroups.Address getLeader() throws Exception{
		org.jgroups.Address clusterLeader = null;
		JChannel c = SetChannel.channel;
		if(SetChannel.members == null){
			System.out.println("Waiting for cluster..");
			System.out.println(c);
			Thread.sleep(200);
			getLeader();
		}else{
			
				byte[] buf = Util.objectToByteBuffer(new message.RequestLeader());
				System.out.println(buf);
				Message msg = new Message(null,null,buf);
				c.send(msg);
			
		}
		return clusterLeader;
	}
	
	public static void main(String[] args) throws Exception{
		System.setProperty("java.net.preferIPv4Stack" , "true");
		// uncomment and force address if not working
		//System.setProperty("jgroups.bind_addr" , "192.168.2.17");
		
		Server start = new Server();
		start.init();
		while(true){
			Thread.sleep(1000);
			if (SetChannel.members != null){
				System.out.println(SetChannel.members);
			}
		}
		
		
		
	// I think this is where the main entry point to the program should be
	// Maybe all servers should run this file
	
	// Multicast group, node discovery with jgroups probably
	
	// Define methods to do with timeouts and then calling leader election
	
	// Define how messages are sent (maybe zmq, or native socket implementations)
	}
}
