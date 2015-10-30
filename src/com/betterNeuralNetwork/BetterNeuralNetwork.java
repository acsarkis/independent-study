package com.betterNeuralNetwork;

import java.util.LinkedList;

import com.example.Example;
import com.interfaces.EagerClassifier;
import com.utilities.DataManagement;

import Jama.Matrix;

public class BetterNeuralNetwork implements EagerClassifier {
	private final int numIterations = 10;
	private int currentIteration;
	private LinkedList<Example> trainSet;
	private final DataManagement dataManagement = new DataManagement();
	private LinkedList<NNLayer> layerActivity = new LinkedList<NNLayer>();
	private LinkedList<NNLayer> sigmoidActivity = new LinkedList<NNLayer>();
	
	private NNLayer trainingLayer;
	private NNLayer hiddenNNLayer;
	private NNLayer exitNNLayer;
	
	public BetterNeuralNetwork(LinkedList<Example> trainSet) {
		this.trainSet = trainSet;
		this.getParameters();
		this.runTraining();
	}

	@Override
	public void getParameters() {
		
	}

	@Override
	public void runTraining() {
		this.currentIteration = 0;
		this.trainingLayer = new NNLayer(this.trainSet);
		this.hiddenNNLayer = new NNLayer(this.trainingLayer.getNumCols(), this.trainingLayer.getNumRows());
		this.exitNNLayer = new NNLayer(this.trainingLayer.getNumRows(), 1);
		while(! this.isConverged()) {
			System.out.println(this.isConverged());
			this.runBackwardsProp(this.runForwardProp(this.trainingLayer));
		}
	}
	
	private boolean isConverged() {
		this.currentIteration++;
		return this.currentIteration > this.numIterations;
	}
	
	private NNLayer runForwardProp(NNLayer input) {
		NNLayer output = new NNLayer(input.getCopyOfMatrix());
		
		output.setAsLayerMultiply(this.hiddenNNLayer);
		this.layerActivity.add(new NNLayer(output.getCopyOfMatrix()));
		output.applySigmoid();
		this.sigmoidActivity.add(new NNLayer(output.getCopyOfMatrix()));
		
		output.setAsLayerMultiply(this.exitNNLayer);
		this.layerActivity.add(new NNLayer(output.getCopyOfMatrix()));
		output.applySigmoid();
		//this.sigmoidActivity.add(new NNLayer(output.getCopyOfMatrix()));
		
		return output;
	}
	
	private void runBackwardsProp(NNLayer output) {
		Matrix realOutputs = this.dataManagement.getClassificationMatrix(this.trainSet);
		
		// yHat - y
		NNLayer gradient = new NNLayer(output.getCopyOfMatrix().minus(realOutputs));
		NNLayer scalar = this.layerActivity.pollLast();
		scalar.applySigmoidDerivative();
		
		// multiply the two
		NNLayer delta3 = gradient.getMatrixScalarMultiply(scalar);
		
		NNLayer appliedSigmoid = this.sigmoidActivity.peekFirst();
		appliedSigmoid.setAsTranspose();
		
		NNLayer djdw2 = appliedSigmoid.multiply(gradient);
		
		NNLayer delta2 = delta3.multiply(this.exitNNLayer.getTranspose());
		NNLayer activity = this.layerActivity.pollLast();
		activity.applySigmoidDerivative();
		delta2.setAsLayerMultiply(activity);
		
		NNLayer djdw1 = this.trainingLayer.getTranspose().multiply(delta2);
		
		this.applyGradientDescent(5.5, djdw1, djdw2);
	}
	
	private void applyGradientDescent(double scalar, NNLayer djdw1, NNLayer djdw2) {
		djdw1.applyScalar(scalar);
		this.hiddenNNLayer.setAsSubtract(djdw1);
		
		djdw2.applyScalar(scalar);
		this.exitNNLayer.setAsSubtract(djdw2);
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		
	}
}