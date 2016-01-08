package com.mvc.model.classifiers.neuralNetwork.algorithms;

public abstract class Function implements FunctionInterface {
	protected double input = 0;
	
	public void setInput(double input) {
		this.input = input;
	}
}
