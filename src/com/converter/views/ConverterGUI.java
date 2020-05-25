package com.converter.views;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;

import javax.swing.*;

import com.converter.controllers.TransformationToImage;
import com.converter.models.Link;

public class ConverterGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static JFrame frame;
	private static JPanel bgPanel;
	private static JPanel panel;
	private static JPanel panel2;
	private static JPanel panel3;
	private static JScrollPane scrollPane;
	
	public static void initGUI() throws IOException {
		
		frame = new JFrame("PDF Converter");
		ImageIcon icon = new ImageIcon("D:/JAVA/FileConverter/src/com/converter/icon.png");
		frame.setIconImage(icon.getImage());
		frame.setLocation(250, 150);
		frame.setSize(500, 350);
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
					
					TransformationToImage.displayFiles(answer);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		bgPanel = new JPanel();
		
		panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setPreferredSize(new Dimension(480,40));
		panel.add(label);
		panel.add(tField);
		panel.add(button);
		
		panel2 = new JPanel(new BorderLayout());
		panel2.setPreferredSize(new Dimension(480,250));
		
		panel3 = new JPanel(new BorderLayout());
		
		scrollPane = new JScrollPane(panel3);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		panel2.add(scrollPane, BorderLayout.CENTER);
		bgPanel.add(panel);
		bgPanel.add(panel2);
		
		frame.getContentPane().add(bgPanel);
		frame.setResizable(false);
		frame.setVisible(true);
		
	}
	
	public static void displayFiles(Set<Link> files) {
		
		int i = 10;
		
		panel3.revalidate();
		panel3.repaint();

		panel3.removeAll();
		panel3.setPreferredSize(new Dimension(480, files.size() * 30));
		
		for (Link file : files) {

			JLabel label = new JLabel(file.getName());
			label.setIcon(new ImageIcon("D:/JAVA/FileConverter/src/com/converter/pdf.png"));
			label.setBounds(10,i,200,20);
			label.setLocation(10, i);
			label.addMouseListener(new MouseAdapter() {
				
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() % 2 == 0 && !e.isConsumed()) {
						try {
							TransformationToImage.generateImageFromPDF(label.getText(), ".jpg");
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			panel3.add(label);
			i += 30;
		}
		panel2.repaint();
	}
}