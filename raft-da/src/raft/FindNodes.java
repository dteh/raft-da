package raft;
import java.util.ArrayList;
import java.util.List;

import org.jgroups.*;
import org.jgroups.stack.IpAddress;

public class FindNodes {
	JChannel channel;
	List<String> addresses = new ArrayList<String>();

	
	private void start() throws Exception{
		channel = new JChannel();
		channel.connect("ClusterChat");
		getIPs();
	}
	
	public void kill() throws Exception{
		channel.close();
	}
	
	private void getIPs() throws Exception{
		while(true){
			Thread.sleep(1000);

			System.out.println(channel.getView().getMembers());
			List<org.jgroups.Address> members = channel.getView().getMembers();
			for (org.jgroups.Address member: members){
				PhysicalAddress physicalAddr = (PhysicalAddress)channel.down(new Event(Event.GET_PHYSICAL_ADDRESS,member));
				IpAddress ipAddr = (IpAddress)physicalAddr;
				System.out.println(ipAddr.getIpAddress().getHostAddress());
				addresses.add(ipAddr.getIpAddress().getHostAddress());
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new FindNodes().start();

	}

}
