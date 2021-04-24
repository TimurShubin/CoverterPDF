package com.example.test.utils;

import java.io.File;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

/*
 * Cleaning folder with zip archive after 5 minutes
 */
@Service
public class FolderCleaner {

	private static volatile Map<String, Long> currentFolders = new ConcurrentHashMap<>();
	
	public synchronized Map<String, Long> getCurrentFolders() {
		return currentFolders;
	}
	
	public void addItemToFolderCleaner(String pathToFolder, Long time) {
		currentFolders.put(pathToFolder, time);
		System.out.println("New folder " + pathToFolder + " (" + time + ") " + currentFolders.size());
	}
	
	public void startCounter() {
		Timer timer = new Timer();
		int begin = 0;
		int timeInterval = 60000; // check folders every 1 minute
		
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// delete unused folders
				currentFolders.forEach((p, t) -> removeFolder(p, t));
			}
		}, begin, timeInterval);
		
	}

	private void removeFolder(String folderPath, Long time) {

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Long currentTime = timestamp.getTime();
		   
		if ((currentTime - time) >= 300000) {
			File index = new File(folderPath);
			if (index.exists()) {
				String[] entries = index.list();
				for(String s: entries){
				    File currentFile = new File(index.getPath(), s);
				    currentFile.delete();
				}
			}
			index.delete();
			currentFolders.remove(folderPath);
		}
	}

}
