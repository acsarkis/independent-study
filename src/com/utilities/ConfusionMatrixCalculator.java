package com.utilities;

import java.util.HashSet;
import java.util.LinkedList;

import com.example.Example;


public class ConfusionMatrixCalculator {
	private final String positiveSetLabel = "Positive Label Set: ";
	private final String negativeSetLabel = "Negative Label Set: ";
	
	private HashSet<Integer> positiveLabels;
	private HashSet<Integer> negativeLabels;
	private int truePositives = 0;
	private int falsePositives = 0;
	private int trueNegatives = 0;
	private int falseNegatives = 0;
	private int total = 0;
	private int correct = 0;
	
	public ConfusionMatrixCalculator() {} 
	
	public void calcAndWipe(final LinkedList<Example> testSet) {
		this.calculateConfusionMatrix(testSet);
		this.printInformation();
		this.wipeValues();
	}
	
	public void wipeValues() {
		this.truePositives = 0;
		this.falsePositives = 0;
		this.trueNegatives = 0;
		this.falseNegatives = 0;
	}
	
	public void setClassificationSet(LinkedList<Example> testSet) {
		HashSet<Integer> countSet = new HashSet<Integer>();
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
		this.positiveLabels = new HashSet<Integer>();
		this.negativeLabels = new HashSet<Integer>();
		this.setClassificationSet(testSet);
		
		System.out.println(this.positiveSetLabel);
		this.printSet(this.positiveLabels);
		
		System.out.println(this.negativeSetLabel);
		this.printSet(this.negativeLabels);
		
		for(Example example : testSet) {
			if(example.getClassification() == example.getPrediction()) {
				// correct
				this.correct++;
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
			this.total++;
		}
		this.printInformation();
	}
	
	public void printSet(HashSet<Integer> set) {
		System.out.print("{ ");
		for(Integer key : set) {
			System.out.print(key + ", ");
		}
		System.out.println("}");
	}
	
	public void printInformation() {
		System.out.println("TN: " + this.trueNegatives + " FN: " + this.falseNegatives);
		System.out.println("FP: " + this.falsePositives + " TP: " + this.truePositives);
		System.out.println("Accuracy: " + (double)this.correct / (double)this.total);
	}
}
