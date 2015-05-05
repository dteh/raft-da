package raft;
import java.util.ArrayList;
import java.util.List;
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
public class FindNodes implements Runnable{
	JChannel channel;
	List<String> tmpadd = new ArrayList<String>();
	List<String> addresses = new ArrayList<String>();

	public JChannel getChannel(){
		return channel;
	}
	public void run(){
		try{
			channel = new JChannel();
			channel.connect("raft");
			getIPs();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	public void kill(){
		channel.close();
	}
	
	private void getIPs() throws Exception{
		while(true){
			tmpadd.clear();
			Thread.sleep(1000);
			List<org.jgroups.Address> members = channel.getView().getMembers();
			for (org.jgroups.Address member: members){
				PhysicalAddress physicalAddr = (PhysicalAddress)channel.down(new Event(Event.GET_PHYSICAL_ADDRESS,member));
				IpAddress ipAddr = (IpAddress)physicalAddr;
				tmpadd.add(ipAddr.getIpAddress().getHostAddress());
			}
			addresses = new ArrayList<String>(tmpadd);
		}
	}
}
