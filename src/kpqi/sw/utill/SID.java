package kpqi.sw.utill;

import java.util.Random;

/**
 * Storage ID
 * @author Kpqi5858
 */
public class SID {
	
	private static char[] list = new char[] {
		'a','b','c','d','e','f','1','2','3','4','5','6','7','8','9','0'
	};
	
	private String id;
	
	public SID(String s) {
		id = s;
	}
	public static SID random() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 8; i++) {
			sb.append(list[new Random().nextInt(15)]);
		}
		return new SID(sb.toString());
	}
	
	public String toString() {
		return id;
	}
}
