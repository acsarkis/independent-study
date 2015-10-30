package com.userInterface.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.userInterface.TextDropDown;
import com.userInterface.UserInterfaceConstants;

public class DatasetSelectView extends JPanel {
	private int width;
	private int height;
	private final String selectText = "Select a dataset";
	private TextDropDown dataSelector;
	
	public DatasetSelectView(int width) {
		this.width = width;
		this.height = 35;
		this.setBackground(Color.YELLOW);
		this.setLayout(new BorderLayout());
		
		JLabel topLabel = new JLabel(this.selectText);
		this.add(topLabel, BorderLayout.NORTH);
		
		
		this.dataSelector = new TextDropDown(UserInterfaceConstants.DATASETS, false);
		this.add(this.dataSelector, BorderLayout.CENTER);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
