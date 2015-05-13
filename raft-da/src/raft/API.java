package raft;

import java.util.NoSuchElementException;

import raft.RaftNode;
import raft.InstructionApplier;

public class API {
	
	/**
	 * This must be called FIRST by the program implementing the library as a base state
	 * to apply changes to.
	 * @param state
	 */
	public static void setState(Object state){
		RaftNode.setStateObject(state);
	}
	
	/**
	 * Run this to connect to the cluster and begin the algorithm
	 * @param applier
	 * @throws Exception
	 */
	public static void init(InstructionApplier applier) throws Exception{
		RaftNode start = new RaftNode();
		RaftNode.apply = applier;
		start.init();
	}
	
	/**
	 * Client needs to use this when pushing a new instruction onto the state
	 */
	public static void pushInstruction(Object obj){
		if(RaftNode.state.State.equals("Follower") || RaftNode.state.State.equals("Candidate")){
			try{
				SetChannel.channel.send(RaftNode.LEADER, new message.LogEntry(obj));
			}catch(Exception e){
				e.printStackTrace();
			}
		}else if(RaftNode.state.State.equals("Leader")){
				RaftNode.apply.ApplyInstruction(obj);
				RaftNode.setNewStateAvailable(true);
		}
	}

	// Also client needs to while(true){ RaftNode.getStateObject()} to display data
}
