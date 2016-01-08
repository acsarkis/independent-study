package com.mvc.model;

import java.util.LinkedList;

import com.example.Example;

public interface EagerClassifier {
	public void setParameter(String parameter);
	public void runTraining();
	public void runTesting(LinkedList<Example> testSet);
	public void runTraining(LinkedList<Example> trainingSet);
	public String[] getParameterOptions();
	public void wipeValues();
}
