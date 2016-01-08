package com.mvc.model.classifiers.neuralNetwork;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;
import com.mvc.model.classifiers.neuralNetwork.algorithms.SigmoidAlgorithm;
import com.mvc.model.classifiers.neuralNetwork.algorithms.SigmoidDerivativeAlgorithm;
import com.mvc.view.subviews.UserInterfaceConstants;
import com.utilities.DataManagement;

import Jama.Matrix;

public class NeuralNetwork implements EagerClassifier {
	private final int numIterations = 20000;
	private final double learningRate = .01;
	private int currentIteration;
	private LinkedList<Example> trainingSet;
	private final DataManagement dataManagement = new DataManagement();
	private LinkedList<NNLayer> layerActivity = new LinkedList<NNLayer>();
	private LinkedList<NNLayer> sigmoidActivity = new LinkedList<NNLayer>();
	private NNLayer expectedOutputs;
	private NNLayer z2;
	private NNLayer z3;
	private NNLayer a2;
	private NNLayer yHat;
	
	private NNLayer trainingLayer;
	private NNLayer w1;
	private NNLayer w2;
	private NNLayer djdw1;
	private NNLayer djdw2;
	
	public NeuralNetwork() {
		this.trainingSet = new LinkedList<Example>();
	}
	
	public NeuralNetwork(LinkedList<Example> trainSet) {
		this.trainingSet = trainSet;
		this.expectedOutputs = new NNLayer(this.dataManagement.getClassificationMatrix(trainSet));
		this.runTraining();
	}

	@Override
	public void runTraining() {
		this.currentIteration = 0;
		this.trainingLayer = new NNLayer(this.trainingSet);
		this.w1 = new NNLayer(this.trainingLayer.getNumCols(), this.trainingLayer.getNumRows());
		this.w2 = new NNLayer(this.trainingLayer.getNumRows(), 1);
		while(! this.isConverged()) {
			System.out.println(this.isConverged());
			this.runForwardProp(this.trainingLayer);
			this.runBackwardsProp();
		}
	}
	
	private boolean isConverged() {
		this.currentIteration++;
		return this.currentIteration > this.numIterations;
	}
	
	private void runForwardProp(NNLayer input) {
		this.z2 = input.multiply(this.w1);
		this.a2 = this.z2.getApplyFunction(new SigmoidAlgorithm());
		this.z3 = this.a2.multiply(this.w2);
		this.yHat = this.z3.getApplyFunction(new SigmoidAlgorithm());
	}
	
	private void runBackwardsProp() {
		this.yHat.outprintSelf();
		NNLayer gradient = this.yHat.getSubtract(this.expectedOutputs);
		this.z3.setApplyFunction(new SigmoidDerivativeAlgorithm());
		
		NNLayer delta3 = gradient.getElementWiseMultiply(this.z3);
		this.djdw2 = this.a2.getTranspose().multiply(delta3);
		
		this.z2.setApplyFunction(new SigmoidDerivativeAlgorithm());
		NNLayer delta2 = delta3.multiply(this.w2.getTranspose().multiply(this.z2));
		this.djdw1 = this.trainingLayer.getTranspose().multiply(delta2);
		
		this.applyGradientDescent(this.learningRate);
	}
	
	private void applyGradientDescent(double scalar) {
		this.djdw1.applyScalar(scalar);
		this.w1.setAddition(this.djdw1);
		//this.djdw1.outprintSelf();
		
		//this.w2.outprintSelf();
		this.djdw2.applyScalar(scalar);
		this.w2.setAddition(this.djdw2);
		//this.djdw2.outprintSelf();
		//this.w2.outprintSelf();
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		NNLayer testingSet = new NNLayer(testSet);
		this.runForwardProp(testingSet);
	}

	@Override
	public void runTraining(LinkedList<Example> trainingSet) {
		this.expectedOutputs = new NNLayer(this.dataManagement.getClassificationMatrix(trainingSet));
		this.currentIteration = 0;
		this.trainingSet = trainingSet;
		this.trainingLayer = new NNLayer(this.trainingSet);
		this.w1 = new NNLayer(this.trainingLayer.getNumCols(), this.trainingLayer.getNumRows());
		this.w2 = new NNLayer(this.trainingLayer.getNumRows(), 1);
		while(! this.isConverged()) {
			this.runBackwardsProp();
		}
	}

	@Override
	public String[] getParameterOptions() {
		return UserInterfaceConstants.NULL_STRING_ARRAY;
	}

	@Override
	public void setParameter(String parameter) { }

	@Override
	public void wipeValues() { }
}