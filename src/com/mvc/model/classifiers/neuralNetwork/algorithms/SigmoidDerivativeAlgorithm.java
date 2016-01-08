package com.mvc.model.classifiers.neuralNetwork.algorithms;

public class SigmoidDerivativeAlgorithm extends Function implements FunctionInterface {
	
	@Override
	public double execute() {
		return 1.0 / (1.0 + Math.pow(Math.E, -input));
	}

}
