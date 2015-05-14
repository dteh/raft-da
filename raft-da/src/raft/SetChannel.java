package raft;

import java.util.ArrayList;
import java.util.List;
import org.jgroups.*;

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

public class SetChannel extends ReceiverAdapter implements Runnable{
	public static JChannel channel;
	List<String> tmpadd = new ArrayList<String>();
	List<String> addresses = new ArrayList<String>();
	static List<org.jgroups.Address> members;
	static ReceiveMessage receiver = new ReceiveMessage();

	
	/**
	 * Thread start procedure
	 */
	public void run(){
		try{
			channel = new JChannel();
			channel.connect("raft");
			channel.setReceiver(this);
			System.out.println(channel.getView().getMembers());
			getAddresses();
		}
		catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * Sets actions to perform on receive of message
	 */
	public void receive(Message msg){
		
		try{
			receiver.parse(msg);
		}catch(Exception e){
			System.out.println(e);
		}
	}
	
	/**
	 * Closes the channel cleanly *done*
	 */
	public void kill(){
		channel.close();
	}
	
	/**
	 * Gets the IPs available in the cluster
	 * (Is this necessary? Perhaps replace with node ID finding)
	 * @throws Exception
	 */
	private void getAddresses() throws Exception{
		while(true){
			Thread.sleep(200);
			members = channel.getView().getMembers();
			/*
			 * This converts member IDs in cluster to IP addresses
			 * Probably don't need this...
			for (org.jgroups.Address member: members){
				PhysicalAddress physicalAddr = (PhysicalAddress)channel.down(new Event(Event.GET_PHYSICAL_ADDRESS,member));
				IpAddress ipAddr = (IpAddress)physicalAddr;
				tmpadd.add(ipAddr.getIpAddress().getHostAddress());
			*/	
		}
	}
}
