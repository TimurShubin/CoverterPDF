package com.converter;

import java.io.IOException;

import com.converter.controllers.PDFToImage;

public class Converter {

	private PDFToImage pdfConverter;
	// I can add converter PDFToText and other types of converters in future
	
	Converter() {
		this.pdfConverter = new PDFToImage();
	}
	
	public void init() throws IOException {
		pdfConverter.init();
	}
	
	public static void main(String[] args) throws IOException {
		Converter converter = new Converter();
		converter.init();
	}

}
