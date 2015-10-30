package com.neuralNetwork;


import Jama.Matrix;

public class NeuralNetworkDriver {
	public static void main(String[] args) { 
		double[][] array = 
			{{3,5},
			{5,1},
			{10,2}};
		
		double[][] array2 = 
			{{.75},
			{.82},
			{.93}};
		
		Matrix input = new Matrix(array);
		Matrix actualOutputs = new Matrix(array2);
		NeuralNetwork neuralNetwork = new NeuralNetwork(input, actualOutputs);
		/*neuralNetwork.runForwardProp();
		neuralNetwork.runBackProp();
		neuralNetwork.runBackProp();
		neuralNetwork.runBackProp();
		neuralNetwork.runBackProp();
		neuralNetwork.runBackProp();*/
	}
}
