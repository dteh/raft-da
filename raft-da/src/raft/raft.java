package raft;

public class raft {
	public static void main(String[] args) throws Exception{
		FindNodes group = new FindNodes();
		Thread groupThread = new Thread(group);	
		groupThread.start();
		while(true){
			System.out.println(group.addresses);
			Thread.sleep(2000);
		}
	}
	
}
