package raft;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import org.jgroups.*;
import org.jgroups.stack.IpAddress;

/**
 * 
 * @author danielteh
 * FindNodes()
 * Uses broadcast to find the addresses of all other nodes who
 * have joined the 'channel'. Saves list of IPs to a list. Rescans
 * for new addresses once every second
 *
 * Address list can be called from the class var 'addresses' 
 */
public class FindNodes {
	JChannel channel;
	List<String> tmpadd = new ArrayList<String>();
	List<String> addresses = new ArrayList<String>();

	
	private void start() throws Exception{
		channel = new JChannel();
		channel.connect("ClusterChat");
		getIPs();
	}
	
	public void kill(){
		channel.close();
	}
	
	private void getIPs() throws Exception{
		while(true){
			tmpadd.clear();
			Thread.sleep(1000);
			System.out.println(channel.getView().getMembers());
			List<org.jgroups.Address> members = channel.getView().getMembers();
			for (org.jgroups.Address member: members){
				PhysicalAddress physicalAddr = (PhysicalAddress)channel.down(new Event(Event.GET_PHYSICAL_ADDRESS,member));
				IpAddress ipAddr = (IpAddress)physicalAddr;
				System.out.println(ipAddr.getIpAddress().getHostAddress());
				tmpadd.add(ipAddr.getIpAddress().getHostAddress());
			}
			java.util.Collections.copy(addresses, tmpadd);
		}
	}
	
	public static void main(String[] args) throws Exception{
		new FindNodes().start();

	}

}
