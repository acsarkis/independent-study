package com.neuralNetwork;


import java.util.Random;

import Jama.Matrix;

public class Layer {
	Matrix weights;
	
	Layer(Matrix weights) {
		this.weights = weights;
	}
	
	Layer(int rows, int columns) {
		// for values not bound in 0-1
		this.weights = Matrix.random(rows, columns);
	}
	
	void applySigmoid() {
		for(int x = 0; x < this.weights.getRowDimension(); x++) {
			for(int y = 0; y < this.weights.getColumnDimension(); y++) {
				this.weights.set(x, y, this.calcSigmoid(this.weights.get(x, y)));
			}
		}
	}
	
	void applySigmoidDerivative() {
		for(int x = 0; x < this.weights.getRowDimension(); x++) {
			for(int y = 0; y < this.weights.getColumnDimension(); y++) {
				this.weights.set(x, y, this.calcDerivativeSigmoid(this.weights.get(x, y)));
			}
		}
	}
	
	void applyScalar(double scalar) {
		this.weights.timesEquals(scalar);
	}
	
	/* Sigmoid calculation */
	private double calcSigmoid(double input) {
		return (1/(1 + Math.pow(Math.E,(-1.0 * input))));
	}
	
	/* Derivative  Sigmoid calculation */
	private double calcDerivativeSigmoid(double input) {
		return this.calcSigmoid(input) * (1.0 - this.calcSigmoid(input));
	}
	
	void setAsMultiply(Layer layer) {
		this.weights = this.weights.times(layer.getMatrix());
	}
	
	Layer getMultiply(Layer layer) {
		return new Layer(this.weights.times(layer.getMatrix()));
	}
	
	public Layer subtract(Layer subBy) {
		return new Layer(this.weights.minus(subBy.getMatrix()));
	}
	
	public void setMatrixScalarMultiply(Layer input) {
		Matrix inputMat = input.getMatrix();
		
		if(inputMat.getColumnDimension() > 1 || inputMat.getRowDimension() != this.weights.getRowDimension()) {
			System.out.println("Scalar multiply given invalid matrix!");
			return;
		}
		
		for(int i = 0; i < inputMat.getRowDimension(); i++) {
			double scalar = inputMat.get(i, 0);
			for(int x = 0; x < this.weights.getColumnDimension(); x++) {
				double currentValue = this.weights.get(i, x) * scalar;
				this.weights.set(i, x, currentValue);
			}
		}
	}
	
	public Layer getMatrixScalarMultiply(Layer input) {
		Matrix inputMat = input.getMatrix();
		Matrix myMat = this.weights;
		
		if(inputMat.getColumnDimension() > 1 || inputMat.getRowDimension() != this.weights.getRowDimension()) {
			System.out.println("Scalar multiply given invalid matrix!");
			return this;
		}
		
		for(int i = 0; i < inputMat.getRowDimension(); i++) {
			double scalar = inputMat.get(i, 0);
			for(int x = 0; x < this.weights.getColumnDimension(); x++) {
				double currentValue = this.weights.get(i, x) * scalar;
				myMat.set(i, x, currentValue);
			}
		}
		return new Layer(myMat);
	}
	
	void setAsTranspose() {
		this.weights = this.weights.transpose();
	}
	
	Layer getTranspose() {
		return new Layer(this.weights.transpose());
	}
	
	void minusEquals(Layer input) {
		this.weights.minusEquals(input.getMatrix());
	}
	
	Matrix getMatrix() {
		return this.weights;
	}
	
	void speak() {
		// Note: The integers are not related to the dimensions of the matrix.
		this.weights.print(1, 3);
	}
	
}
