package com.userInterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.userInterface.views.ClassifierSelectView;
import com.userInterface.views.DatasetSelectView;
import com.userInterface.views.ButtonControlView;

public class EditorPanel extends JPanel {
	private ClassifierSelectView classifierSelectView;
	private DatasetSelectView datasetSelectView;
	private ButtonControlView operationsView;
	
	public EditorPanel() {
		this.setBackground(Color.BLACK);
		this.setLayout(new FlowLayout());
		
		this.createPanels();
	}
	
	private void createPanels() {
		int childPanelWidth = UserInterfaceConstants.EDITOR_FRAME_WIDTH / 2;
		double scaling = .93;
		childPanelWidth *= scaling;
		
		this.classifierSelectView = new ClassifierSelectView(childPanelWidth);
		this.add(classifierSelectView);
		
		this.datasetSelectView = new DatasetSelectView(childPanelWidth);
		this.add(this.datasetSelectView);
		
		this.operationsView = new ButtonControlView(childPanelWidth);
		this.add(this.operationsView);
	}
}
