package com.controller;

import com.interfaces.EagerClassifier;
import com.nullClassifier.NullClassifier;
import com.userInterface.UserInterfaceConstants;

public class ClassifierController {
	private String classifierString = UserInterfaceConstants.NULL_STRING;
	private EagerClassifier selectedClassifer = new NullClassifier();
	
	
	
	public ClassifierController() {
		 
	}
	
	public void modClassifier(String classifier) {
		this.classifierString = classifier;
	}
	
	public void modParameter(String parameter) {
		
	}
	
	public void modDataset(String dataset) {
		
	}
	
	public void runClassificationTask() {
		
	}
}
