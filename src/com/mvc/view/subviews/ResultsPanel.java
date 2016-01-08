package com.mvc.view.subviews;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.mvc.model.ClassifierMetricsModel;
import com.mvc.view.MetricsView;

public class ResultsPanel extends JPanel {
	private MetricsView metricsView;
	private ClassifierMetricsModel classifierMetricsModel;
	private final double childWidthScale = .94;
	
	public ResultsPanel(ClassifierMetricsModel classifierMetricsModel) {
		this.setBackground(Color.DARK_GRAY);
		this.classifierMetricsModel = classifierMetricsModel;
		int width = (int) (((double)UserInterfaceConstants.EDITOR_FRAME_WIDTH)* this.childWidthScale);
		this.metricsView = new MetricsView(width, this.classifierMetricsModel);
		this.classifierMetricsModel.addObserver(this.metricsView);
		this.add(metricsView);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(UserInterfaceConstants.EDITOR_FRAME_WIDTH, UserInterfaceConstants.EDITOR_FRAME_HEIGHT * (2/3));
	}
}
