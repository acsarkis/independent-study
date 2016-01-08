package com.mvc.model.classifiers.neuralNetwork.algorithms;

public class SigmoidAlgorithm extends Function implements FunctionInterface {
	@Override
	public double execute() {
		return 1.0 / (1.0 + Math.exp(- this.input));
	}

}
