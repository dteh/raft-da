package raft;
import org.jgroups.Message;
import org.jgroups.Address;
import org.jgroups.util.*;

public class ReceiveMessage {
	/*
	 * Message parsing
	 */
	public void parse(Message msg) throws Exception{
		message.RaftMessage m = (message.RaftMessage)msg.getObject();
		System.out.println(m.name);
		if(m.name == "RequestLeader"){
			ResponseLeader(msg.getSrc());
		}
		if(m.name == "ResponseLeader"){
			RaftNode.LEADER = (Address)m.payload;
		}
		if(m.name == "RequestVote"){
			// do something
		}
		if(m.name == "AppendEntries"){
			// do something
		}
		
	}
	
	/**
	 * If the node has a value for the leader, it will respond to the requester with
	 * its value
	 * @param requester - address to respond to
	 */
	private void ResponseLeader(Address requester){
		if(RaftNode.LEADER != null){
			try{
				byte[] buf = Util.objectToByteBuffer(new message.ResponseLeader(RaftNode.LEADER));
				Message m = new Message(requester,buf);
				SetChannel.channel.send(m);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
