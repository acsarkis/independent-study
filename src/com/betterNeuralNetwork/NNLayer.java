package com.betterNeuralNetwork;

import java.util.ArrayList;
import java.util.LinkedList;

import com.example.Example;
import com.utilities.DataManagement;

import Jama.Matrix;

public class NNLayer {
	private Matrix matrix;
	private final DataManagement dataManagement = new DataManagement();
	
	public NNLayer(Matrix matrix) {
		this.matrix = matrix;
	}
	
	public NNLayer(LinkedList<Example> trainSet) {
		this.dataManagement.convertListToMat(trainSet);
		this.matrix = this.dataManagement.convertListToMat(trainSet);
	}
	
	public NNLayer(int rows, int columns) {
		this.matrix = Matrix.random(rows, columns);
	}
	
	public void applySigmoid() {
		for(int x = 0; x < this.matrix.getRowDimension(); x++) {
			for(int y = 0; y < this.matrix.getColumnDimension(); y++) {
				this.matrix.set(x, y, this.calcSigmoid(this.matrix.get(x, y)));
			}
		}
	}
	
	public void applySigmoidDerivative() {
		for(int x = 0; x < this.matrix.getRowDimension(); x++) {
			for(int y = 0; y < this.matrix.getColumnDimension(); y++) {
				this.matrix.set(x, y, this.calcDerivativeSigmoid(this.matrix.get(x, y)));
			}
		}
	}
	
	public NNLayer multiply(NNLayer input) {
		return new NNLayer(this.matrix.times(input.getMatrix()));
	}
	
	public Matrix getMatrix() {
		return this.matrix;
	}
	
	public NNLayer getSubtract(NNLayer input) {
		return new NNLayer(this.matrix.minus(input.getCopyOfMatrix()));
	}
	
	public void setAsSubtract(NNLayer input) {
		this.matrix.minusEquals(input.getCopyOfMatrix());
	}
	
	public void setAsTranspose() {
		this.matrix = this.matrix.transpose();
	}
	
	public NNLayer getTranspose() {
		return new NNLayer(((Matrix) this.matrix.clone()).transpose());
	}
	
	public void applyScalar(double scalar) {
		this.matrix.timesEquals(scalar);
	}
	
	/* Sigmoid calculation */
	private double calcSigmoid(double input) {
		return (1/(1 + Math.pow(Math.E,(-1.0 * input))));
	}
	
	/* Derivative  Sigmoid calculation */
	private double calcDerivativeSigmoid(double input) {
		return this.calcSigmoid(input) * (1.0 - this.calcSigmoid(input));
	}
	
	public Matrix getCopyOfMatrix() {
		return (Matrix) this.matrix.clone();
	}
	
	public int getNumRows() {
		return this.matrix.getRowDimension();
	}
	
	public int getNumCols() {
		return this.matrix.getColumnDimension();
	}
	
	public void setAsLayerMultiply(NNLayer multiplyBy) {
		this.matrix = this.matrix.times(multiplyBy.getCopyOfMatrix());
	}
	
	public void setMatrixScalarMultiply(NNLayer input) {
		Matrix inputMat = input.getMatrix();
		
		if(inputMat.getColumnDimension() > 1 || inputMat.getRowDimension() != this.matrix.getRowDimension()) {
			System.out.println("Scalar multiply given invalid matrix!");
			return;
		}
		
		for(int i = 0; i < inputMat.getRowDimension(); i++) {
			double scalar = inputMat.get(i, 0);
			for(int x = 0; x < this.matrix.getColumnDimension(); x++) {
				double currentValue = this.matrix.get(i, x) * scalar;
				this.matrix.set(i, x, currentValue);
			}
		}
	}
	
	public NNLayer getMatrixScalarMultiply(NNLayer input) {
		Matrix inputMat = input.getMatrix();
		Matrix myMat = this.matrix;
		
		if(inputMat.getColumnDimension() > 1 || inputMat.getRowDimension() != this.matrix.getRowDimension()) {
			System.out.println("Scalar multiply given invalid matrix!");
			return this;
		}
		
		for(int i = 0; i < inputMat.getRowDimension(); i++) {
			double scalar = inputMat.get(i, 0);
			for(int x = 0; x < this.matrix.getColumnDimension(); x++) {
				double currentValue = this.matrix.get(i, x) * scalar;
				myMat.set(i, x, currentValue);
			}
		}
		return new NNLayer(myMat);
	}
	
	public void speak() {
		for(int r = 0; r < this.matrix.getRowDimension(); r++) {
			for(int c = 0; c < this.matrix.getColumnDimension(); c++) {
				System.out.print(this.matrix.get(r, c));
			}
			System.out.println();
		}
		System.out.println();
	}
}
