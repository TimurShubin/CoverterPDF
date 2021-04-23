package com.example.test.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface IUploader {

	public String upload(String key, MultipartFile file);
	
}
