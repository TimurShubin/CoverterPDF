package com.converter.controllers;

import java.io.IOException;
import java.util.Set;

import com.converter.models.Link;

public interface PDFInterface {

	public void displayFiles(String p) throws IOException;
	
	public Set<Link> getFilesFromPath(String p, String ext) throws IOException;
	
	public void processFiles(String p, Set<Link> files) throws IOException;
	
}
