package com.example.test.controllers;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.test.services.IUploader;

@Controller
public class FileUploadController {

	@Autowired
	private IUploader uploadService;
	
	public volatile Map<String, MultipartFile> files = new ConcurrentHashMap<>();
	//public Collection<MultipartFile> values = files.values();
	
	@PostMapping("/uploadFile")
	public ResponseEntity<Object> handleFileUpload(@RequestParam("file") MultipartFile file) throws Exception {
		if (!file.isEmpty()) {
			files.put(file.getName(), file);
			//Stream<MultipartFile> valuesStream = values.stream();
			
			files.forEach((s, v) -> uploadService.upload(s, v));
			
			String name = file.getOriginalFilename();
			//String fName = "src/main/resources/uploads/" + (name.substring(0, name.length() - 4)).hashCode() + "/images_from_pdf.zip";
			//String f = new File(fName).getAbsolutePath();
			return new ResponseEntity<>("/download/" + (name.substring(0, name.length() - 4)).hashCode(), HttpStatus.OK);
		} else {
			System.out.println("File not found");
		}
		return new ResponseEntity<>("Invalid file.", HttpStatus.BAD_REQUEST);
	}
}
