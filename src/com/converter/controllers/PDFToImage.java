package com.converter.controllers;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.converter.PackageUpdater;
import com.converter.models.Link;

public class PDFToImage {

	public static Map<String, Set<Link>> history = new HashMap<>();
	private static String path;
	
	public void init() throws IOException {

		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			displayFiles(sc.next());
			new PackageUpdater();
		}
	}

	private static Set<Link> getFilesFromPath(String p, String ext) throws IOException {
		
		Set<Link> files = new HashSet<>();
		
		File dir = new File(p);
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				
				if (item.isDirectory()) {	
					
					p = item.getPath();
					getFilesFromPath(p, ext);
					
				} else {
					
					String[] tmp = item.getName().split("\\.");
					if (tmp.length > 0) {	
						if (tmp[tmp.length - 1].equals(ext)) {
							Link link = new Link();
							link.setName(item.getName()); //
							link.setSize(item.length() / 1024);
							files.add(link);
						}
					}
					tmp = null;
				}
			}
		}
		return files;
	}
	
	public static void displayFiles(String p) throws IOException {

		if (!history.containsKey(p)) {
			// In current session Map not created yet,
			// therefore we can create Map for place Links to files
			System.out.println("Start processing");
			System.out.println("Creating JPG directories");
			path = p;
			Set<Link> f = getFilesFromPath(p, "pdf");
			System.out.println("Found files: " + f.size());
			history.put(p, f);
			processFiles(p, f);
		} else {
			// Map is exist, therefore we can check changes in her
			// if Map size differs from initial size, then we can compare them
			Set<Link> f = getFilesFromPath(p, "pdf");
			if (history.get(p).size() != f.size()) {
				// perhaps, we found some changes
				// let's find Sets difference
				System.out.println("Start processing");
				
				if (history.get(p).size() > f.size()) {
					
					history.get(p).removeAll(f);
					processFiles(p, history.get(p));
				} else {
					
					f.removeAll(history.get(p));
					processFiles(p, f);
					history.get(p).addAll(f);
				}
			}
		}
	}
	
	public static void processFiles(String p, Set<Link> files) {
		for (Link file : files) {
			try {
				generateImageFromPDF(p + "/" + file.getName(), ".jpg");
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("Processing completed");
	}
	
	public static void generateImageFromPDF(String filename, String extension) throws IOException {
		PDDocument document = PDDocument.load(new File(filename));
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
		
	    System.out.println("Processing file " + filename + ": " + document.getNumberOfPages() + " pages");

		(new File(path + "/JPG")).mkdir();
        
		String inserted = "JPG/"; 
        int index = filename.lastIndexOf("/");
        filename = filename.substring(0, index + 1) + inserted + filename.substring(index + 1);
        
	    for (int page = 0; page < document.getNumberOfPages(); ++page) {
	        BufferedImage bim;
			bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format("%s_%d.%s", filename.replace(".pdf", ""), page + 1, extension), 150);
	    }
	    document.close();
	    
	    System.out.println("File " + filename + " is processed.");
	}
	
}