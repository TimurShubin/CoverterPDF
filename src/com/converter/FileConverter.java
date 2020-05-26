package com.converter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.converter.models.Link;

public class FileConverter {

	public static Map<String, Set<Link>> history = new HashMap<>();
	private static Set<Link> files = new HashSet<>();
	private static String path;
	private static String temp;	
	
	public static void main(String[] args) throws IOException {

		Scanner sc = new Scanner(System.in);		
		if(sc.hasNext()) {
			displayFiles(sc.next());
			new PackageUpdater();			
		}
	}

	// проверяет наличие файлов с расширением *ext*
	// добавляет файлы в множество files
	// аргумент ext позволяет искать файлы с разными расширениями
	//
	private static Set<Link> getFilesFromPath(String p, String ext) throws IOException {
		
		temp = p; // ошибка!
		
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

		System.out.println("Начало обработки");
		System.out.println("Создание каталогов JPG");

		if (!history.containsKey(p)) {
			path = p;
			Set<Link> f = getFilesFromPath(p, "pdf");
			System.out.println("Найдено файлов: " + f.size());
			history.put(p, f);
			handleFiles(f);
		} else {
			System.out.println("Найдено файлов: " + history.size());
			handleFiles(history.get(p));
		}
	}
	
	public static void handleFiles(Set<Link> files) {
		for (Link file : files) {
			try {
				generateImageFromPDF(file.getName(), ".jpg");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		System.out.println("Обработка завершена");
	}
	
	public static void generateImageFromPDF(String filename, String extension) throws IOException {
		
		PDDocument document = PDDocument.load(new File(path + "/" + filename));
	    PDFRenderer pdfRenderer = new PDFRenderer(document);
		
	    System.out.println("Обрабатывается файл " + filename + ": " + document.getNumberOfPages() + " стр.");
	    
	    for (int page = 0; page < document.getNumberOfPages(); ++page) {
	        BufferedImage bim;
			(new File(path + "/JPG")).mkdir();
			bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format(path + "/JPG/%s_%d.%s", filename, page + 1, extension), 150);
	    }
	    document.close();
	    
	    System.out.println("Файл " + filename + " обработан.");
	}

}
