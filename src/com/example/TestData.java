package com.example;

import java.util.LinkedList;

public class TestData {
	private LinkedList<Integer> positiveSet = new LinkedList<Integer>();
	private LinkedList<Integer> negativeSet = new LinkedList<Integer>();
	
	private int total = 0;
	private int falsePositives = 0;
	private int truePositives = 0;
	private int falseNegatives = 0;
	private int trueNegatives = 0;
	private int numCorrect = 0;
	private int numIncorrect = 0;
	

	public TestData() { }
	
	public TestData(LinkedList<Integer> classifications) { 	
		// to handle generating the confusion matrix, I assign classifications to the negative or positive example sets
		for(int i = 0; i < classifications.size(); i++) {
			if(i < classifications.size() / 2) {
				this.positiveSet.add(classifications.get(i));
			} else {
				this.negativeSet.add(classifications.get(i));
			}
		}
	}
	
	public void makePrediction(int prediction, int actualClassification) {
		if(prediction == actualClassification) {
			this.numCorrect++;
			if(this.positiveSet.contains(actualClassification)) {
				// True positive
				this.truePositives++;
			} else {
				this.trueNegatives++;
			}
		} else {
			this.numIncorrect++;
			if(this.negativeSet.contains(actualClassification)) {
				// False positive
				this.falsePositives++;
			} else {
				// false negative
				this.falseNegatives++;
			}
		}
		this.total++;
	}
	
	public void sayPosNegSets() {
		System.out.print("Positive classifications: ");
		for(int i = 0; i < this.positiveSet.size(); i++) {
			System.out.print(" " + this.positiveSet.get(i));
		}
		System.out.println();
		
		System.out.print("Negative classifications: ");
		for(int i = 0; i < this.negativeSet.size(); i++) {
			System.out.print(" " + this.negativeSet.get(i));
		}
		System.out.println();
	}
	
	public double getAccuracy() {
		return (double)this.numCorrect / (double)this.total;
	}
	
	public int getFalsePositives() {
		return this.falsePositives;
	}
	public int getTruePositives() {
		return this.truePositives;
	}
	public int getTrueNegatives() {
		return this.trueNegatives;
	}
	public int getFalseNegatives() {
		return this.falseNegatives;
	}
}
