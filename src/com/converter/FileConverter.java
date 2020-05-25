package com.converter;

import java.io.*;
import javax.swing.*;
import com.converter.views.ConverterGUI;

public class FileConverter {
	
	public static void main(String[] args) throws IOException {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					ConverterGUI.initGUI();
					new PackageUpdater();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
