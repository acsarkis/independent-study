package com.interfaces;

import java.util.LinkedList;

import com.example.Example;

public interface EagerClassifier {
	public void getParameters();
	public void runTraining();
	public void runTesting(LinkedList<Example> testSet);
}
