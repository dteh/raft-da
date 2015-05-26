package raft;

import java.util.HashMap;

public class tests {
	static HashMap<String,Integer> a;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		a = new HashMap<String,Integer>();
		a.put("a",4);
		a.put("a",3);
		
		System.out.println(a.get("b"));

	}

}
