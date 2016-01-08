package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mvc.view.subviews.TextDropDown;
import com.utilities.fileProcessing.FileConstants;

public class DatasetSelectView extends JPanel {
	private int width;
	private int height;
	private final String selectText = "Select a dataset";
	private TextDropDown<String> dataSelector;
	
	public DatasetSelectView(int height) {
		this.width = 100;
		this.height = height;
		this.setBackground(Color.RED);
		this.setLayout(new BorderLayout());
		
		JLabel topLabel = new JLabel(this.selectText);
		this.add(topLabel, BorderLayout.NORTH);
		
		
		this.dataSelector = new TextDropDown(FileConstants.DATASETS, false);
		this.add(this.dataSelector, BorderLayout.CENTER);
	}
	
	public String getSelectedDataset() {
		return this.dataSelector.getSelectedItem();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
}
