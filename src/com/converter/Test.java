package com.converter;

import java.io.File;

public class Test {
	private static String path = "D:/test";
	
	public static void main(String[] args) {

		getPDF(path);
		
	}
	
	public static void getPDF(String p) {
		File d = new File(p);
		if (d.isDirectory()) {
			//if(d.length() > 0) {
				for (File item : d.listFiles()) {
					if (item.isDirectory()) {
						p = item.getPath();
						// System.out.println(item.getPath());
						getPDF(p);
					} else {
						if (item.getName().length() > 4 && item.getName().substring(item.getName().length() - 3, item.getName().length()).equals("pdf")) {					
							System.out.println(item.getName());
						}
					}
				}
			//}
		}
	}
}
