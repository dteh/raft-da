package raft;
import java.util.ArrayList;


public class Server {
	public static void main(String[] args) throws Exception{
		System.setProperty("java.net.preferIPv4Stack" , "true");
		// uncomment and force address if not working
		//System.setProperty("jgroups.bind_addr" , "192.168.2.17");

		FindNodes addresses = new FindNodes();
		new Thread(addresses).start();
		while(true){
			ArrayList<String> ipAddresses = new ArrayList<String>(addresses.addresses);
			int ipAddressesSz = ipAddresses.size(); 
			System.out.println(ipAddresses);
			Thread.sleep(5000);
		}
		
	// I think this is where the main entry point to the program should be
	// Maybe all servers should run this file
	
	// Multicast group, node discovery with jgroups probably
	
	// Define methods to do with timeouts and then calling leader election
	
	// Define how messages are sent (maybe zmq, or native socket implementations)
	}
}
