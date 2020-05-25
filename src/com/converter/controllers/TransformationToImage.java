package com.converter.controllers;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.converter.models.Link;
import com.converter.views.ConverterGUI;

public class TransformationToImage {

	public static Map<String, Set<Link>> history = new HashMap<>();
	private static Set<Link> files = new HashSet<>();;
	private static String path;
	
	// проверяет наличие файлов с расширением *ext*
	// добавляет файлы в множество files
	// аргумент ext позволяет искать файлы с разными расширениями
	//
	private static Set<Link> getFilesFromPath(String p, String ext) throws IOException {
		path = p;
		
		File dir = new File(p);
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				
				if (item.isDirectory()) {	
					
					p = item.getPath();
					getFilesFromPath(p,ext);
					
				} else {
					
					String[] tmp = item.getName().split("\\.");
					if(tmp.length > 0) {	
						if (tmp[tmp.length - 1].equals(ext)) {
							Link link = new Link();
							link.setName(item.getName());
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
	
	// метод подгружает файлы на экран
	// 
	public static void displayFiles(String p) throws IOException {
	
		if (!history.containsKey(p)) {
			Set<Link> f = getFilesFromPath(p, "pdf");
			history.put(p, f);
			ConverterGUI.displayFiles(f);
		} else {
			ConverterGUI.displayFiles(history.get(p));
		}
	}
	
	public static void generateImageFromPDF(String filename, String extension) throws IOException {
		
		PDDocument document = PDDocument.load(new File(path + "/" + filename));
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
	    
	    for (int page = 0; page < document.getNumberOfPages(); ++page) {
	        BufferedImage bim;
			(new File(path + "/IMG")).mkdir();
			bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format(path + "/IMG/%s_%d.%s", filename, page + 1, extension), 300);
	    }
	    document.close();
	}
	
	public static String getPath() {
		return path;
	}
	
}
