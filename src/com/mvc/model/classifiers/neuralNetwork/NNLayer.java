package com.mvc.model.classifiers.neuralNetwork;

import java.util.ArrayList;
import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.classifiers.neuralNetwork.algorithms.Function;
import com.mvc.model.classifiers.neuralNetwork.algorithms.FunctionInterface;
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
	
	
	public void setAsElementWiseMultiplication(NNLayer input) {
		this.matrix = this.matrix.arrayTimes(input.getMatrix());
	}
	
	public NNLayer multiply(NNLayer input) {
		return new NNLayer(this.clone().getMatrix().times(input.getMatrix()));
	}
	
	public Matrix getMatrix() {
		return this.matrix;
	}
	
	public void setAsTranspose() {
		this.matrix = this.matrix.transpose();
	}
	
	public NNLayer getTranspose() {
		return new NNLayer(this.clone().getMatrix().transpose());
	}
	
	public void applyScalar(double scalar) {
		this.matrix.timesEquals(scalar);
	}
	
	public NNLayer clone() {
		return new NNLayer((Matrix) this.matrix.clone());
	}
	
	public int getNumRows() {
		return this.matrix.getRowDimension();
	}
	
	public int getNumCols() {
		return this.matrix.getColumnDimension();
	}
	
	public void setAsElementWiseMultiply(NNLayer input) {
		Matrix inputMat = input.getMatrix();
		for(int r = 0; r < this.getNumRows(); r++) {
			for(int c = 0; c < this.getNumCols(); c++) {
				this.matrix.set(r, c, this.matrix.get(r, c) * inputMat.get(r, c));
			}
		}
	}
	
	public NNLayer getElementWiseMultiply(NNLayer input) {
		Matrix outputMat = new Matrix(this.getNumRows(), this.getNumCols());
		
		Matrix inputMat = input.getMatrix();
		for(int r = 0; r < this.getNumRows(); r++) {
			for(int c = 0; c < this.getNumCols(); c++) {
				outputMat.set(r, c, this.matrix.get(r, c) * inputMat.get(r, c));
			}
		}
		
		return new NNLayer(outputMat);
	}
	
	public NNLayer getSubtract(NNLayer input) {
		Matrix myMatrix = this.matrix;
		Matrix inputMatrix = input.matrix;
		Matrix outputMatrix = new Matrix(this.getNumRows(), this.getNumCols());
		
		outputMatrix = myMatrix.minus(inputMatrix);
		return new NNLayer(outputMatrix);
	}
	
	public void setSubtract(NNLayer input) {
		Matrix inputMat = input.getMatrix();
		
		this.matrix.minusEquals(inputMat);
	}
	
	public void setAddition(NNLayer input) {
		Matrix inputMat = input.getMatrix();
		this.matrix.plusEquals(inputMat);
	}
	
	public void setApplyFunction(Function function) {
		for(int r = 0; r < this.getNumRows(); r++) {
			for(int c = 0; c < this.getNumCols(); c++) {
				double num = this.matrix.get(r, c);
				function.setInput(num);
				this.matrix.set(r, c, function.execute());
			}
		}
	}
	
	public NNLayer getApplyFunction(Function function) {
		Matrix output = new Matrix(this.getNumRows(), this.getNumCols());
		
		for(int r = 0; r < this.getNumRows(); r++) {
			for(int c = 0; c < this.getNumCols(); c++) {
				double num = this.matrix.get(r, c);
				function.setInput(num);
				output.set(r, c, function.execute());
			}
		}
		
		return new NNLayer(output);
	}
	
	public void outprintSelf() {
		for(int r = 0; r < this.matrix.getRowDimension(); r++) {
			for(int c = 0; c < this.matrix.getColumnDimension(); c++) {
				System.out.print(this.matrix.get(r, c));
			}
			System.out.println();
		}
		System.out.println();
	}
}
