package com.mvc.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.mvc.model.ClassifierMetricsModel;
import com.mvc.view.subviews.UserInterfaceConstants;

public class MetricsView extends JPanel implements Observer {
	private ClassifierMetricsModel classifierMetrics;
	
	private final int height;
	private final int width;
	JLabel[][] jlabelArray;
	
	private final int gridWidth = 3;
	private final int gridHeight = 3;
	
	private final String TPtext = "TP: ";
	private final String TNtext = "TN: ";
	private final String FPtext = "FP: ";
	private final String FNtext = "FN: ";
	private final String nullText = UserInterfaceConstants.NULL_STRING;
	
	private JPanel predictedPanel;
	private final String predictedText = "predicted";
	
	private JPanel actualPanel;
	private final String actualText = "actual";
	
	private final JLabel nullLabel = new JLabel(UserInterfaceConstants.NULL_STRING);
	private JPanel tablePanel;
	
	private JPanel supplmentPanel;
	
	private JPanel accuracyPanel;
	private final String accuracyText = "accuracy: ";
	private JLabel accuracyLabel;
	
	private JPanel precisionRecallPanel;
	private final String precisionText = "precision: ";
	private JLabel precisionLabel;
	
	private final String recallText = "recall: ";
	private JLabel recallLabel;
	
	public MetricsView(int width, ClassifierMetricsModel classifierMetrics) {
		this.width = width;
		this.height = 150;
		this.setLayout(new BorderLayout());
		this.setBackground(Color.yellow);
		this.classifierMetrics = classifierMetrics;
		this.createTableLabels();
		this.createTable();
		this.createSupplementMetrics();
	}
	
	public void createTableLabels() {
		this.actualPanel = new JPanel();
		this.actualPanel.add(new JLabel(this.actualText));
		this.add(this.actualPanel, BorderLayout.NORTH);
		
		this.predictedPanel = new JPanel();
		this.predictedPanel.add(new JLabel(this.predictedText));
		this.add(this.predictedPanel, BorderLayout.WEST);
	}
	
	public void createTable() {
		this.jlabelArray = new JLabel[this.gridWidth][this.gridHeight];
		this.tablePanel = new JPanel(new GridLayout(this.gridWidth, this.gridHeight));
		
		for(int x = 0; x < this.gridWidth; x++) {
			for(int y = 0; y < this.gridHeight; y++) {
				if(x == 1 && y == 1) {
					this.jlabelArray[x][y] = new JLabel(this.TNtext + this.classifierMetrics.getTNs());
				} else if(x == 1 && y == 2) {
					this.jlabelArray[x][y] = new JLabel(this.FNtext + this.classifierMetrics.getFNs());
				} else if(x == 2 && y == 1) {
					this.jlabelArray[x][y] = new JLabel(this.FPtext + this.classifierMetrics.getFPs());
				} else if(x == 2 && y == 2) {
					this.jlabelArray[x][y] = (new JLabel(this.TPtext + this.classifierMetrics.getTPs()));
				} else if((x == 1 && y == 0) || (x == 0 && y == 1)) {
					this.jlabelArray[x][y] = (new JLabel(getHashSetString(this.classifierMetrics.getNegativeLabelSet())));
				} else if((x == 2 && y == 0) || (x == 0 && y == 2)) {
					this.jlabelArray[x][y] = (new JLabel(getHashSetString(this.classifierMetrics.getPositiveLabelSet())));
				} else {
					this.jlabelArray[x][y] = (new JLabel(this.nullText));
				}
				this.addLabel(this.jlabelArray[x][y]);
			}
		}
		this.add(this.tablePanel, BorderLayout.CENTER);
	}
	
	public void updateTable() {
		for(int x = 0; x < this.gridWidth; x++) {
			for(int y = 0; y < this.gridHeight; y++) {
				if(x == 1 && y == 1) {
					this.jlabelArray[x][y].setText(this.TNtext + this.classifierMetrics.getTNs());
				} else if(x == 1 && y == 2) {
					this.jlabelArray[x][y].setText(this.FNtext + this.classifierMetrics.getFNs());
				} else if(x == 2 && y == 1) {
					this.jlabelArray[x][y].setText(this.FPtext + this.classifierMetrics.getFPs());
				} else if(x == 2 && y == 2) {
					this.jlabelArray[x][y].setText(this.TPtext + this.classifierMetrics.getTPs());
				} else if((x == 1 && y == 0) || (x == 0 && y == 1)) {
					this.jlabelArray[x][y].setText(getHashSetString(this.classifierMetrics.getNegativeLabelSet()));
				} else if((x == 2 && y == 0) || (x == 0 && y == 2)) {
					this.jlabelArray[x][y].setText(getHashSetString(this.classifierMetrics.getPositiveLabelSet()));
				} else {
					this.jlabelArray[x][y].setText(this.nullText);
				}
			}
		}
	}
	
	public String getHashSetString(HashSet<Integer> input) {
		StringBuilder output = new StringBuilder("{");
		
		int i = 0;
		for(Integer key : input) {
			if(i == input.size() - 1) {
				output.append(Integer.toString(key));
			} else {
				output.append(Integer.toString(key) + ", ");
			}
			i++;
		}
		output.append("}");
		
		return output.toString();
	}
	
	public void createSupplementMetrics() {
		this.supplmentPanel = new JPanel(new GridLayout(2,1));
		
		this.precisionRecallPanel = new JPanel(new GridLayout(1, 2));
		this.precisionLabel = new JLabel(this.precisionText + this.calcPrecision(true));
		this.recallLabel = new JLabel(this.recallText + this.calcRecall(true));
		this.precisionRecallPanel.add(this.precisionLabel);
		this.precisionRecallPanel.add(this.recallLabel);
		
		this.supplmentPanel.add(this.precisionRecallPanel);
		
		this.accuracyPanel = new JPanel();
		this.accuracyLabel = new JLabel(this.accuracyText + " " + this.calcAccuracy(false));
		this.accuracyPanel.add(accuracyLabel);
		
		this.supplmentPanel.add(this.accuracyPanel);
		
		this.add(this.supplmentPanel, BorderLayout.SOUTH);
	}
	
	private void updateSupplmentMetrics() {
		this.precisionLabel.setText(this.precisionText + " " + this.calcPrecision(true));
		this.accuracyLabel.setText(this.accuracyText + " " + this.calcAccuracy(false));
		this.recallLabel.setText(this.recallText + " " + this.calcRecall(true));
	}
	
	private String calcAccuracy(boolean isDecimal) {
		double accuracyNum = this.classifierMetrics.getTPs() + this.classifierMetrics.getTNs();
		double accuracyDenom = accuracyNum + this.classifierMetrics.getFNs() + this.classifierMetrics.getFPs();
		if(accuracyDenom == 0.0) {
			return this.formatDouble(accuracyDenom) + "%";
		}
		double accuracyPercent = accuracyNum / accuracyDenom;
		if(isDecimal) {
			return this.formatDouble(accuracyPercent);
		}
		return this.formatDouble(accuracyPercent * 100.0);
	}
	
	private String calcPrecision(boolean isDecimal) {
		double precisionDenominator = this.classifierMetrics.getTPs() + this.classifierMetrics.getFPs();
		if(precisionDenominator == 0) {
			return this.formatDouble(precisionDenominator);
		}
		double numerator = this.classifierMetrics.getTPs();
		if(isDecimal) {
			return this.formatDouble(numerator / precisionDenominator);
		}
		return this.formatDouble((numerator / precisionDenominator) * 100.0);
	}
	
	private String calcRecall(boolean isDecimal) {
		double precisionDenominator = this.classifierMetrics.getTPs() + this.classifierMetrics.getFNs();
		if(precisionDenominator == 0) {
			return this.formatDouble(precisionDenominator);
		}
		double numerator = this.classifierMetrics.getTPs();
		if(isDecimal) {
			return this.formatDouble(numerator / precisionDenominator);
		}
		return this.formatDouble((numerator / precisionDenominator) * 100.0);
	}
	
	private String formatDouble(double d) {
		DecimalFormat df = new DecimalFormat("#.###");
		return df.format(d);
	}
	
	private void addLabel(JLabel label) {
		label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.tablePanel.add(label);
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.updateTable();
		this.updateSupplmentMetrics();
	}
}
