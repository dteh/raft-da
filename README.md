# raft-da
Raft for DA

**Requires JGroups (tested on 3.6.3.final)**

Compilation:
    - Include JGroups 3.6.3final in the class path
    - Can be compiled as a 'runnable jar' in order to test the protocol (there is a main class which simulates connecting to a cluster and maintaining leadership)
    - Must be compiled as a non-runnable jar to be implemented as a library

-----
Code Structure:

Package: raft
API - Library access point for implementation
InstructionApplier - Interface to design a class to implement how state instructions are processed
LeaderThread - Thread for actions undertaken when a client is designated as leader
RaftNode - Main entry point for the library
ReceiveMessage - How the implementation behaves when receiving various messages
SetChannel - Initialization of the underlying transport mechanisms
TimeoutThread - Timeout detection

Package: message
AppendEntries - Heartbeats and state replication
LogEntry - New instruction to apply to global state
RaftMessage - All messages implement this base class
RequestLeader - Request for information about leader
RequestVote - Calling a vote
ResponseLeader - Response to a RequestLeader message
Vote - Response to a vote

Package: state
Candidate - Actions undertaken by candidate
Follower - Actions undertaken by follower
Leader - Actions undertaken by leader
State - All states implement this abstract class

Not used/deprecated
raft.tests
message.Response

-----
Raft for Distributed Algorithms COMP90020
Daniel Teh, Felipe Costa, Sergio Freschi