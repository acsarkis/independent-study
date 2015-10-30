package com.nullClassifier;

import java.util.LinkedList;

import com.example.Example;
import com.interfaces.EagerClassifier;

public class NullClassifier implements EagerClassifier {

	@Override
	public void getParameters() { }

	@Override
	public void runTraining() { }

	@Override
	public void runTesting(LinkedList<Example> testSet) { }
}
