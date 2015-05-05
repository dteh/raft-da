package raft;
import org.jgroups.*;

public class MessageListener extends ReceiverAdapter{
	JChannel channel;
	public MessageListener(JChannel c){
		channel = c;
	}
	public void Listen() throws Exception{
		channel = new JChannel();
		channel.setReceiver(this);
		channel.connect("raft");
	}
	
	public void receive(Message msg){
		//Execute runInstruction(msg.getObject());
	}
	
}
