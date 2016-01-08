package com.mvc.model.classifiers.norvigNetwork;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;

import Jama.Matrix;

public class Network implements EagerClassifier {
	private double[][] inputWeights;
	private double[][] outputWeights;
	private LinkedList<Double[][]> layers;
	private final LinkedList<Example> trainingSet;
	private final double randomRange = 3;
	private final int networkLayers = 3;
	private final int numHiddenLayers = this.networkLayers - 2;
	private int rows;
	private int columns;
	
	public Network(LinkedList<Example> trainingSet) {
		this.trainingSet = trainingSet;
		this.columns = this.trainingSet.getFirst().getNumFeatures();
		this.rows = this.trainingSet.size();
		
		this.runTraining();
	}
	
	public void initWeights(int num) {
		Double[][] newWeights = new Double[this.rows][this.columns];
		for(int r = 0; r < this.rows; r++) {
			for(int c = 0; c < this.columns; c++) {
				newWeights[r][c] = this.getRandomNum(0.0, 2.5);
			}
		}
		this.layers.add(newWeights);
	}
	
	private double getRandomNum(double min, double max) {
		return Math.random() * (max - min) + min;
	}
	
	public Double[][] initInputWeights(Example example) {
		Double[][] inputWeights = new Double[this.rows][this.columns];
		for(int c = 0; c < example.getNumFeatures(); c++) {
			inputWeights[0][c] = example.getFeatureDoubleAt(c);
		}
		this.layers.add(inputWeights);
		return inputWeights;
	}

	@Override
	public void setParameter(String parameter) {
		
	}

	@Override
	public void runTraining() {
		this.initWeights(this.numHiddenLayers);
		Double[][] in = new Double[this.rows][this.columns];
		for(Example example : this.trainingSet) {
			Double[][] featureSet = this.initInputWeights(example);
			for(int currentLayer = 2; currentLayer < this.networkLayers; currentLayer++) {
				Double[][] layer = this.layers.get(currentLayer);
				// for node j in layer currentLayer do: 
				for(int j = 0; j < this.layers.get(currentLayer)[0].length; j++) {
					double sum = 0.0;
					for(int i = 0; i < this.rows; i++) {
						sum += layer[j][i] * this.inputWeights[0][i];
					}
					//this.inputWeights[j][i] = new SigmoidAlgorithm();
				}
			}
		}
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		
	}

	@Override
	public void runTraining(LinkedList<Example> trainingSet) {
		
	}

	@Override
	public String[] getParameterOptions() {
		return null;
	}

	@Override
	public void wipeValues() {
		
	}
}
