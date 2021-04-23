package com.example.test.services;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.utils.FolderCleaner;

@Service
public class UploadService implements IUploader {

	@Autowired
	FolderCleaner folderCleaner;
	
	private final String path = "src/main/resources/uploads/";
	private String filename;
	
	@Override
	public String upload(String key, MultipartFile file) {
		
		String name = file.getOriginalFilename();
		String result = null;
		
		// 1. create folder for pdf converting to images and saving them.
		String folderName = path + (name.substring(0, name.length() - 4)).hashCode();
		(new File(folderName)).mkdir();
		
		// 2. push pdf to new folder
        filename = folderName + "/" + name;
        writePdf(file);
		
        // 3. convert pdf to image
        try {
			generateImageFromPDF(filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 4. zip images
        try {
			result = zipImages(folderName);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // 5.remove temporary files
        try {
			removeTemporaryFiles(folderName);
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // 6. start 5 minutes counter after that remove folder with zip archive
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        folderCleaner.addItemToFolderCleaner(folderName, timestamp.getTime());
        
        return result;
	}
	
	private void writePdf(MultipartFile file) {
		try {
			byte[] bytes = file.getBytes();
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filename)));
	        stream.write(bytes);
	        stream.close();
		} catch (Exception e) {
			e.getMessage();
		}
	}
	
	private void generateImageFromPDF(String file) throws IOException {
		String f = new File(file).getAbsolutePath();
		PDDocument document = PDDocument.load(new File(f));
		PDFRenderer pdfRenderer = new PDFRenderer(document);
		System.out.println("Processing file " + file + ": " + document.getNumberOfPages() + " pages");
		for (int page = 0; page < document.getNumberOfPages(); ++page) {
			BufferedImage bim;
			bim = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
			ImageIOUtil.writeImage(bim, String.format("%s_%d.%s", filename.replace(".pdf", ""), page + 1, ".jpg"), 150);
	    }
	    document.close();
	    System.out.println("File " + file + " is processed.");
	}
	
	private String zipImages(String path) throws IOException {
		List<String> srcFiles = getFilesFromPath(path, "jpg");
		String p = path + "/images_from_pdf.zip";
		FileOutputStream fos = new FileOutputStream(p);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (String srcFile : srcFiles) {
            File fileToZip = new File(srcFile);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();
        
        return p;
	}
	
	private void removeTemporaryFiles(String path) throws IOException {
		List<String> srcJpeg = getFilesFromPath(path, "jpg");
		List<String> srcPdf = getFilesFromPath(path, "pdf");
		for (String jpeg : srcJpeg) {
            File file = new File(jpeg);
            file.delete();
		}
		for (String pdf : srcPdf) {
            File file = new File(pdf);
            file.delete();
		}
	}
	
	private List<String> getFilesFromPath(String p, String ext) throws IOException {
		
		List<String> files = new ArrayList<>();
		
		File dir = new File(new File(p).getAbsolutePath());
		if (dir.isDirectory()) {
			for (File item : dir.listFiles()) {
				if (item.isDirectory()) {	
					p = item.getPath();
					getFilesFromPath(p, ext);
				} else {
					String[] tmp = item.getName().split("\\.");
					if (tmp.length > 0) {	
						if (tmp[tmp.length - 1].equals(ext)) {
							files.add(item.getAbsolutePath());
						}
					}
					tmp = null;
				}
			}
		}
		return files;
	}

}
