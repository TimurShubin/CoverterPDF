package com.converter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import javax.swing.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

public class FileConverter {
	
	private static HashSet<String> files;
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
	// добавляет файлы в множество
	public static void getPathToFiles(String p) throws IOException {
		files = new HashSet<>();
		path = p;
		File dir = new File(path);
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				if (item.isDirectory()) {
					System.out.println(item.getName());
//					getPathToFiles(item.getName());
				} else {
					if (item.getName().substring(item.getName().length() - 3, item.getName().length()).equals("pdf")) {
						files.add(item.getName() + " \t " + item.length() / 1024 + " kB");
					}
				}
			}
			displayFiles(files);
		}
	}
	
	public static void displayFiles(HashSet<String> dFiles) {
		ConverterGUI.displayFiles(dFiles);
	}
	
	public static void generateImageFromPDF(String filename, String extension) throws IOException {
	    File f = new File(filename);
		PDDocument document = PDDocument.load(new File(filename));
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
	    ConverterGUI.checkProgress();
	    if(f.mkdir())
	    for (int page = 0; page < document.getNumberOfPages(); ++page) {
	    	System.out.print("|");
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
