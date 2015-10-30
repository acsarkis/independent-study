package com.utilities;

import java.util.LinkedList;

import com.example.Example;

import Jama.Matrix;

public class DataManagement {
	public Matrix convertListToMat(LinkedList<Example> input) {
		Matrix returnMatrix = new Matrix(input.size(), input.getFirst().getNumFeatures());
		
		for(int r = 0; r < input.size(); r++) {
			for(int c = 0; c < input.getFirst().getNumFeatures(); c++) {
				returnMatrix.set(r, c, input.get(r).getFeatureDoubleAt(c));
			}
		}
		
		return returnMatrix;
	}
	
	public Matrix getClassificationMatrix(LinkedList<Example> input) {
		Matrix matrix = new Matrix(input.size(), 1);
		
		for(int r = 0; r < input.size(); r++) {
			matrix.set(r, 0, input.get(r).getClassification());
		}
		
		return matrix;
	}
}
