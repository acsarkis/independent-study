package com.mvc.view.subviews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.mvc.controller.ClassifierController;
import com.mvc.model.ClassifierMetricsModel;
import com.mvc.view.MetricsView;

public class MainWindow extends JFrame {
	private ClassifierController classifierController;
	private EditorPanel editingPanel;
	
	private ClassifierMetricsModel classifierMetricsModel;
	private ResultsPanel resultsPanel;
	private MetricsView metricsView;
	
	private JPanel mainPanel;
	
	public MainWindow() {
		this.doIntermediateInitializationSteps();
		// Do this last!
		this.doFinalInitalzationSteps();
	}
	
	private void doIntermediateInitializationSteps() {
		this.setSize(new Dimension(UserInterfaceConstants.EDITOR_FRAME_WIDTH, UserInterfaceConstants.EDITOR_FRAME_HEIGHT));
		
		this.mainPanel = new JPanel(new BorderLayout());
		
		this.classifierMetricsModel = new ClassifierMetricsModel();
		this.classifierController = new ClassifierController(this.classifierMetricsModel);
		
		this.editingPanel = new EditorPanel(this.classifierController);
		this.mainPanel.add(this.editingPanel, BorderLayout.NORTH);
		
		this.resultsPanel = new ResultsPanel(this.classifierMetricsModel);
		this.mainPanel.add(this.resultsPanel, BorderLayout.CENTER);
	}
	
	private void doFinalInitalzationSteps() {
		this.add(mainPanel);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
}
