package com.converter;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

public class ConverterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JFrame frame;
	private static JPanel panel;
	private static JProgressBar bar;
	
	public static void initGUI() throws IOException {
		frame = new JFrame("PDF Converter");
		frame.setBounds(100, 100, 500, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JLabel label = new JLabel("¬ведите путь к папке с файлами:");
		label.setBounds(20,20,100,30);
		label.setLocation(20, 20);
		
		JTextField tField = new JTextField(15);
		tField.setLocation(20, 40);
		tField.setToolTipText("укажите путь к папке с файлами");
		
		JButton button = new JButton("найти");
		button.setBounds(20, 60, 100, 30);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String answer = tField.getText();
				try {
					FileConverter.getPathToFiles(answer);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.add(label);
		panel.add(tField);
		panel.add(button);
		
		frame.add(panel);
		frame.setResizable(false);
		frame.setVisible(true);	
	}
	
	public static void displayFiles(HashSet<String> files) {
		int i = 50;
		for(String file : files) {
			JLabel label = new JLabel(file);
			System.out.println(file);
			label.setBounds(10,i,200,20);
			label.setLocation(10, i);
			label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount() % 2 == 0 && !e.isConsumed()) {
						try {
							FileConverter.generateImageFromPDF(FileConverter.getPath() + "/" + label.getText(), ".jpg");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			panel.add(label);
			i += 30;
		}
		frame.repaint();
	}
	
	public static void checkProgress() {
		
	}

}