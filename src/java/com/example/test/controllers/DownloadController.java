package com.example.test.controllers;

import java.io.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DownloadController {

	@Autowired
    ServletContext context;
	
	@RequestMapping("/download/{filePath:.+}")
	public void downloadZipArchive(HttpServletRequest request, HttpServletResponse response,
	        @PathVariable("filePath") String filePath) throws IOException {
		try {
			String fName = "src/main/resources/uploads/" + filePath + "/images_from_pdf.zip";
			String f = new File(fName).getAbsolutePath();
			
			//String downloadFolder = context.getRealPath("/resources/uploads/");
	        File file = new File(f);
	        if (file.exists()) {
	        	String mimeType = context.getMimeType(file.getPath());
	        	 
	            if (mimeType == null) {
	                mimeType = "application/octet-stream";
	            }
	
	            response.setContentType(mimeType);
	            response.addHeader("Content-Disposition", "attachment; filename=" + filePath + ".zip");
	            response.setContentLength((int) file.length());
	
	            OutputStream os = response.getOutputStream();
	            FileInputStream fis = new FileInputStream(file);
	            byte[] buffer = new byte[4096];
	            int b = -1;
	
	            while ((b = fis.read(buffer)) != -1) {
	                os.write(buffer, 0, b);
	            }
	
	            fis.close();
	            os.close();
	        } else {
	            System.out.println("Requested " + filePath + " file not found!!");
	        }
		} catch (IOException e) {
            System.out.println("Error:- " + e.getMessage());
        }
        
	}
	
}
