package com.mvc.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Observable;

import com.example.Example;

public class ClassifierMetricsModel extends Observable {
	private int truePositives;
	private int trueNegatives;
	private int falseNegatives;
	private int falsePositives;
	private int[] values = {truePositives, trueNegatives, falseNegatives, falsePositives};
	private HashSet<Integer> positiveLabels = new HashSet<Integer>();
	private HashSet<Integer> negativeLabels = new HashSet<Integer>();
	
	public ClassifierMetricsModel() {
		this.wipeValues();
	}
	
	public void wipeValues() {
		this.positiveLabels = new HashSet<Integer>();
		this.negativeLabels = new HashSet<Integer>();
		this.truePositives = 0;
		this.trueNegatives = 0;
		this.falseNegatives = 0;
		this.falsePositives = 0;
	}
	
	public void setClassificationSet(LinkedList<Example> testSet) {
		HashSet<Integer> countSet = new HashSet<Integer>();
		this.positiveLabels = new HashSet<Integer>();
		this.negativeLabels = new HashSet<Integer>();
		for(Example example : testSet) {
			countSet.add(example.getClassification());
			countSet.add(example.getPrediction());
		}
		
		int i = 0;
		for(Integer key : countSet) {
			if(i % 2 == 0) {
				this.negativeLabels.add(key);
			} else {
				this.positiveLabels.add(key);
			}
			i++;
		}
	}
	
	public void calculateConfusionMatrix(LinkedList<Example> testSet) {
		this.wipeValues();
		this.positiveLabels = new HashSet<Integer>();
		this.negativeLabels = new HashSet<Integer>();
		this.setClassificationSet(testSet);
		
		for(Example example : testSet) {
			if(example.getClassification() == example.getPrediction()) {
				if(this.positiveLabels.contains(example.getClassification())) {
					this.truePositives++;
				} else {
					this.trueNegatives++;
				}
			} else {
				// incorrect classification
				if(this.positiveLabels.contains(example.getClassification())) {
					this.falseNegatives++;
				} else {
					this.falsePositives++;
				}
			}
		}
		this.setChanged();
		this.notifyObservers(1);
	}
	
	public HashSet<Integer> getPositiveLabelSet() {
		return this.positiveLabels;
	}
	
	public HashSet<Integer> getNegativeLabelSet() {
		return this.negativeLabels;
	}
	
	public int getTPs() {
		return this.truePositives;
	}
	
	public int getTNs() {
		return this.trueNegatives;
	}
	
	public int getFPs() {
		return this.falsePositives;
	}
	
	public int getFNs() {
		return this.falseNegatives;
	}
}
