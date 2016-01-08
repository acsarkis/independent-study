package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mvc.model.EagerClassifier;
import com.mvc.model.classifiers.nullClassifier.NullClassifier;
import com.mvc.view.subviews.TextDropDown;
import com.mvc.view.subviews.UserInterfaceConstants;

public class ClassifierSelectView extends JPanel implements ActionListener {
	private int width;
	private int height;
	private JPanel labelPanel;
	private JPanel selectionPanel;
	private EagerClassifier selectedClassifier = new NullClassifier();
	private HashMap<String, String[]> classifierOptionsMap;
	
	TextDropDown<String> classifierSelector;
	TextDropDown<String> classifierOptions;
	
	private final int numSelectors = 2;
	private final String selectClassifierText = "Select Classifier";
	private final String selectAdjustableParameterText = "Select Paramaters";
	
	public ClassifierSelectView(int height) {
		this.width = 220;
		this.height = height;
		this.setBackground(Color.CYAN);
		this.setLayout(new BorderLayout());
		
		this.createLabels();
		this.createSelectors();
	}
	
	private void createLabels() {
		this.labelPanel = new JPanel(new GridLayout(1,this.numSelectors));
		this.labelPanel.setBackground(Color.red);
		
		this.labelPanel.add(new JLabel(this.selectClassifierText));
		
		this.labelPanel.add(new JLabel(this.selectAdjustableParameterText));
		
		this.add(this.labelPanel, BorderLayout.NORTH);
	}
	
	private void createSelectors() {
		this.selectionPanel = new JPanel(new GridLayout(1,this.numSelectors));
		this.selectionPanel.setBackground(Color.red);
		
		this.classifierSelector = new TextDropDown<String>(UserInterfaceConstants.CLASSIFIER_OPTIONS, true);
		this.selectionPanel.add(this.classifierSelector);
		this.classifierSelector.addActionListener(this);
		
		this.createClassifierAggregationMap();
		this.classifierOptions = new TextDropDown<String>(this.classifierOptionsMap);
		this.selectionPanel.add(this.classifierOptions);
		
		this.add(this.selectionPanel);
	}
	
	public void createClassifierAggregationMap() {
		this.classifierOptionsMap = new HashMap<String, String[]>();
		for(String classifier : UserInterfaceConstants.CLASSIFIER_OPTIONS) {
			String[] options = UserInterfaceConstants.STRING_CLASSIFIER_MAPPING.get(classifier).getParameterOptions();
			this.classifierOptionsMap.put(classifier, options);
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public String getSelectedClassifier() {
		return this.classifierSelector.getSelectedItem();
	}
	
	public String getSelectedClassifierParameter() {
		return this.classifierOptions.getSelectedItem();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == this.classifierSelector) {
			String selected = (String) this.classifierSelector.getSelectedItem();
			this.classifierOptions.aggregate(selected);
		}
	}
}
