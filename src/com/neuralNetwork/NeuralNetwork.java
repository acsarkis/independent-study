package com.neuralNetwork;


import java.util.ArrayList;
import java.util.LinkedList;

import Jama.Matrix;

public class NeuralNetwork {
	// the input matrix
	private final Layer input;
	private final Layer actualOutputs;
	private Layer exitLayer;
	private Layer output;
	private LinkedList<Layer> midLayers = new LinkedList<Layer>();
	private LinkedList<Layer> activityOfLayers = new LinkedList<Layer>();
	private LinkedList<Layer> sigmoidOfLayers = new LinkedList<Layer>();
	// how many nodes tall is the hidden layer?
	private int networkHeight = 3;
	private int prevCols = 0;
	private Layer djdw1;
	private Layer djdw2;
	
	NeuralNetwork(Matrix input, Matrix actualOutputs) {
		this.input = new Layer(input);
		this.actualOutputs = new Layer(actualOutputs);
		this.prevCols = input.getColumnDimension(); 
		this.addHiddenLayer();
		this.exitLayer = this.addExitLayer();
	}
	
	void addHiddenLayer() {
		this.midLayers.add(new Layer(this.prevCols, this.networkHeight));
		this.prevCols = this.networkHeight;
	}
	
	Layer addExitLayer() {
		return new Layer(this.networkHeight, 1);
	}
	
	Layer runForwardProp() {
		this.output = new Layer(this.input.getMatrix());
		for(int i = 0; i < this.midLayers.size(); i++) {
			Layer currentLayer = this.midLayers.get(i);
			this.output.setAsMultiply(currentLayer);
			this.activityOfLayers.add(new Layer(this.output.getMatrix()));
			this.output.applySigmoid();
			this.sigmoidOfLayers.add(new Layer(this.output.getMatrix()));
			// Store this for back propagation!
		}
		this.output.setAsMultiply(this.exitLayer);
		this.activityOfLayers.add(new Layer(this.output.getMatrix()));
		this.output.applySigmoid();
		
		System.out.print("FORWARD PROPAGATION OUTPUT: ");
		this.output.speak();
		return this.output;
	}
	
	void applyGradientDescent(double scalar) {
		this.djdw1.applyScalar(scalar);
		this.midLayers.getLast().minusEquals(this.djdw1);
		
		this.djdw2.applyScalar(scalar);
		this.exitLayer.minusEquals(this.djdw2);
		//this.exitLayer.speak();
	}
	
	void runBackProp() {
		this.runForwardProp();
		// yHat - y
		Layer gradient = new Layer(this.output.getMatrix().minus(this.actualOutputs.getMatrix()));
		Layer scalar = new Layer(this.activityOfLayers.getLast().getMatrix());
		scalar.applySigmoidDerivative();
		
		// multiply the two
		Layer delta3 = gradient.getMatrixScalarMultiply(scalar);
		
		Layer appliedSigmoid = new Layer(this.sigmoidOfLayers.peekFirst().getMatrix());
		appliedSigmoid.setAsTranspose();
		this.djdw2 = appliedSigmoid.getMultiply(gradient);
		
		Layer delta2 = delta3.getMultiply(this.exitLayer.getTranspose());
		Layer activity = new Layer(this.activityOfLayers.peekFirst().getMatrix());
		activity.applySigmoidDerivative();
		delta2.setAsMultiply(activity);
		
		this.djdw1 = this.input.getTranspose().getMultiply(delta2);
		
		this.applyGradientDescent(5.5);
	}
}
