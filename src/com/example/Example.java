package com.example;

import java.util.LinkedList;

public class Example {
	private  LinkedList<Integer> features;
	private final int classification;
	private int prediction = -1;
	
	public Example(LinkedList<Integer> rawdataset) {
		LinkedList<Integer> input = rawdataset;
		this.classification = input.pollLast();
		this.features = input;
	}
	
	public void setPrediction(int prediction) {
		this.prediction = prediction;
	}
	
	public int getPrediction() {
		return this.prediction;
	}
	
	public void removeFeatureAtIndex(int index) {
		this.features.remove(index);
	}
	
	public int getNumFeatures() {
		return this.features.size();
	}
	
	public int getClassification() {
		return this.classification;
	}
	
	public int getFeatureAt(int index) {
		return this.features.get(index);
	}
	
	public double getFeatureDoubleAt(int index) {
		return (double) this.features.get(index);
	}
	
	public LinkedList<Integer> getFeatureSet() {
		return this.features;
	}
	
	public boolean tryClassification(int classification) {
		return this.classification == classification;
	}
}
