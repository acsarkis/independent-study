package com.userInterface;

import com.utilities.FileConstants;

public class UserInterfaceConstants {
	// Frame settings
	public static final int EDITOR_FRAME_WIDTH = 550;
	public static final int EDITOR_FRAME_HEIGHT = 400;
	
	// Empty string used by some classes for the null pattern
	public static final String NULL_STRING = "";
	
	// Classifier strings
	public static final String NAIVE_BAYES_TEXT = "Naive Bayes";
	public static final String DECISION_TREE_TEXT = "Decision Tree";
	public static final String NEURAL_NETWORK_TEXT = "Neural Network";
	
	public static final String[] CLASSIFIER_OPTIONS = {NAIVE_BAYES_TEXT, DECISION_TREE_TEXT, NEURAL_NETWORK_TEXT};
	
	// Dataset strings
	public static final String[] DATASETS = {FileConstants.HANDWRITING_FILE_NAME, FileConstants.ZOO_DATASET};
	
}
