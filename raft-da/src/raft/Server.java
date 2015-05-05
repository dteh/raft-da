package raft;
import java.util.ArrayList;
import state.*;


public class Server {
	State state;
	FindNodes addresses;
	private void init(){
		addresses = new FindNodes();
		new Thread(addresses).start();
		MessageListener listen = new MessageListener(addresses.getChannel());
		
		
		
	}
	public static void main(String[] args) throws Exception{
		System.setProperty("java.net.preferIPv4Stack" , "true");
		// uncomment and force address if not working
		//System.setProperty("jgroups.bind_addr" , "192.168.2.17");
		
		Server a = new Server();
		a.init();
		
		// test case (this can be deleted later, just to print output
		/*
		while(true){
			ArrayList<String> ipAddresses = new ArrayList<String>(addresses.addresses);
			int ipAddressesSz = ipAddresses.size(); 
			System.out.println(ipAddresses);
			Thread.sleep(5000);
		}
		*/
		
	// I think this is where the main entry point to the program should be
	// Maybe all servers should run this file
	
	// Multicast group, node discovery with jgroups probably
	
	// Define methods to do with timeouts and then calling leader election
	
	// Define how messages are sent (maybe zmq, or native socket implementations)
	}
}
