package com.userInterface;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	private JPanel resultsPanel;
	private EditorPanel editingPanel;
	private JPanel mainPanel;
	
	public MainWindow() {
		this.doIntermediateInitializationSteps();
		// Do this last!
		this.doFinalInitalzationSteps();
	}
	
	private void doIntermediateInitializationSteps() {
		this.setSize(new Dimension(UserInterfaceConstants.EDITOR_FRAME_WIDTH, UserInterfaceConstants.EDITOR_FRAME_HEIGHT));
		
		this.mainPanel = new JPanel(new GridLayout(0,2));
		
		this.editingPanel = new EditorPanel();
		this.mainPanel.add(this.editingPanel);
	}
	
	private void doFinalInitalzationSteps() {
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
