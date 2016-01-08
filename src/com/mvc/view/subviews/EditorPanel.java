package com.mvc.view.subviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.mvc.controller.ClassifierController;
import com.mvc.view.ButtonControlView;
import com.mvc.view.ClassifierSelectView;
import com.mvc.view.DatasetSelectView;

public class EditorPanel extends JPanel {
	private ClassifierSelectView classifierSelectView;
	private DatasetSelectView datasetSelectView;
	private ButtonControlView operationsView;
	private final ClassifierController classifierController;
	
	public EditorPanel(ClassifierController classifierController) {
		this.classifierController = classifierController;
		
		this.setBackground(Color.BLACK);
		this.setLayout(new FlowLayout());
		
		this.createPanels();
	}
	
	private void createPanels() {
		int childPanelHeight = UserInterfaceConstants.EDITOR_FRAME_HEIGHT / 9;
		double scaling = .93;
		childPanelHeight *= scaling;
		
		this.classifierSelectView = new ClassifierSelectView(childPanelHeight);
		this.add(classifierSelectView);
		
		this.datasetSelectView = new DatasetSelectView(childPanelHeight);
		this.add(this.datasetSelectView);
		
		this.operationsView = new ButtonControlView(childPanelHeight, this);
		this.add(this.operationsView);
	}
	
	public void runSelectedParameters() {
		String dataset = this.datasetSelectView.getSelectedDataset();
		String classifier = this.classifierSelectView.getSelectedClassifier();
		String parameter = this.classifierSelectView.getSelectedClassifierParameter();
		
		this.classifierController.runClassificationTask(classifier, parameter, dataset);
	}
}
