package raft;

// This needs to be how a program applies an instruction to it's global state
// Pass the applier function object to the initialization call in the API
public interface InstructionApplier {
	
	// This must contain a section for if the global state object present
	// on the node is EMPTY. This method must ALSO, setNewStateAvailable
	// to TRUE on application of the instruction.
	public Object ApplyInstruction(Object obj);
}
