package com.converter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.converter.models.Link;
import com.converter.views.ConverterGUI;

public class FileConverter {
	
	private static Set<Link> files = new HashSet<>();;
	private static String path;
	
	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {		
				try {
					ConverterGUI.initGUI();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	// проверяет наличие файлов с расширением pdf
	// добавляет файлы в множество files
	
	public static Set<Link> getPathToFiles(String p) throws IOException {
		path = p;
		File dir = new File(path);
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				if (item.isDirectory()) {
					p = item.getPath();
					getPathToFiles(p);
				} else {
					if (item.getName().substring(item.getName().length() - 3, item.getName().length()).equals("pdf")) {
						Link link = new Link();
						link.setName(item.getName());
						link.setSize(item.length() / 1024);
						files.add(link);
					}
				}
			}
		}
		return files;
	}
	
	// метод подгружает файлы на экран
	
	public static void displayFiles(Set<Link> dFiles) {
		ConverterGUI.displayFiles(dFiles);
	}
	
	public static void generateImageFromPDF(String filename, String extension) throws IOException {
		PDDocument document = PDDocument.load(new File(filename));
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
	    
	    for (int page = 0; page < document.getNumberOfPages(); ++page) {
	        BufferedImage bim = pdfRenderer.renderImageWithDPI(
	          page, 150, ImageType.RGB);
	        ImageIOUtil.writeImage(
	          bim, String.format(path + "/IMG/pdf-%d.%s", page + 1, extension), 300);
	    }
	    document.close();
	}
	
	public static String getPath() {
		return path;
	}
}
