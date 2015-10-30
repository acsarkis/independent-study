package com.userInterface.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.userInterface.TextDropDown;
import com.userInterface.UserInterfaceConstants;

public class ClassifierSelectView extends JPanel implements ActionListener {
	private int width;
	private int height;
	private JPanel labelPanel;
	private JPanel selectionPanel;
	
	TextDropDown classifierSelector;
	TextDropDown classifierOptions;
	
	private final int numSelectors = 2;
	private final String selectClassifierText = "Select Classifier!";
	private final String selectAdjustableParameterText = "Select paramaters!";
	
	public ClassifierSelectView(int width) {
		this.width = width;
		this.height = 35;
		this.setBackground(Color.CYAN);
		this.setLayout(new BorderLayout());
		
		this.createLabels();
		this.createSelectors();
	}
	
	private void createLabels() {
		this.labelPanel = new JPanel(new GridLayout(1,this.numSelectors));
		
		this.labelPanel.add(new JLabel(this.selectClassifierText));
		
		this.labelPanel.add(new JLabel(this.selectAdjustableParameterText));
		
		this.add(this.labelPanel, BorderLayout.NORTH);
	}
	
	private void createSelectors() {
		this.selectionPanel = new JPanel(new GridLayout(1,this.numSelectors));
		
		this.classifierSelector = new TextDropDown(UserInterfaceConstants.CLASSIFIER_OPTIONS, true);
		this.selectionPanel.add(this.classifierSelector);
		this.classifierSelector.addActionListener(this);
		
		this.classifierOptions = new TextDropDown(UserInterfaceConstants.CLASSIFIER_OPTIONS, true);
		this.selectionPanel.add(this.classifierOptions);
		
		this.add(this.selectionPanel);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.classifierSelector) {
			System.out.println("hello?");
		}
	}
}
