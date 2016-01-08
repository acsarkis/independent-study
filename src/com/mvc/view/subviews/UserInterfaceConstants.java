package com.mvc.view.subviews;

import java.util.HashMap;

import com.mvc.model.EagerClassifier;
import com.mvc.model.classifiers.bagging.RandomForest;
import com.mvc.model.classifiers.decisionTree.DecisionTree;
import com.mvc.model.classifiers.naiveBayes.NaiveBayes;
import com.mvc.model.classifiers.neuralNetwork.NeuralNetwork;
import com.mvc.model.classifiers.nullClassifier.NullClassifier;

public class UserInterfaceConstants {
	// Frame settings
	public static final int EDITOR_FRAME_WIDTH = 550;
	public static final int EDITOR_FRAME_HEIGHT = 400;
	
	// Empty string used by some classes for the null pattern
	public static final String NULL_STRING = "";
	public static final String[] NULL_STRING_ARRAY = {UserInterfaceConstants.NULL_STRING};
	
	// Classifier strings
	public static final String NAIVE_BAYES_TEXT = "Naive Bayes";
	public static final String DECISION_TREE_TEXT = "Decision Tree";
	public static final String NEURAL_NETWORK_TEXT = "Neural Network";
	public static final String DECISION_FOREST_TEXT = "Decision Forest";
	
	// classifiers to be displayed in the selection dropdown
	public static final String[] CLASSIFIER_OPTIONS = {NAIVE_BAYES_TEXT, DECISION_TREE_TEXT, DECISION_FOREST_TEXT};
	
	public static final HashMap<String, EagerClassifier> STRING_CLASSIFIER_MAPPING;
    static
    {
    	STRING_CLASSIFIER_MAPPING = new HashMap<String, EagerClassifier>();
    	STRING_CLASSIFIER_MAPPING.put(NAIVE_BAYES_TEXT, new NaiveBayes());
    	STRING_CLASSIFIER_MAPPING.put(DECISION_TREE_TEXT, new DecisionTree());
    	STRING_CLASSIFIER_MAPPING.put(NEURAL_NETWORK_TEXT, new NeuralNetwork());
    	STRING_CLASSIFIER_MAPPING.put(DECISION_FOREST_TEXT, new RandomForest());
    	STRING_CLASSIFIER_MAPPING.put(NULL_STRING, new NullClassifier());
    }
	
}
