package raft;
import org.jgroups.Address;
import org.jgroups.util.*;

public class tests {

	public static void main(String[] args) {
		Address TEST = null;
		try{
			byte[] buf = Util.objectToByteBuffer(TEST);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("test");
		System.out.println(5/2);
	}

}
