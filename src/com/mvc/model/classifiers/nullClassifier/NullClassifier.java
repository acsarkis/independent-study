package com.mvc.model.classifiers.nullClassifier;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;
import com.mvc.view.subviews.UserInterfaceConstants;

public class NullClassifier implements EagerClassifier {

	@Override
	public void runTraining() { }

	@Override
	public void runTesting(LinkedList<Example> testSet) { }

	@Override
	public void setParameter(String parameter) { }

	@Override
	public void runTraining(LinkedList<Example> trainingSet) { }

	@Override
	public String[] getParameterOptions() {
		return UserInterfaceConstants.NULL_STRING_ARRAY;
	}

	@Override
	public void wipeValues() {
		
	}
}
