package com.mvc.model.classifiers.decisionTree;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;
import com.mvc.view.subviews.UserInterfaceConstants;

public class DecisionTree implements EagerClassifier {
	private int maxDepth = 1;
	private DTreeNode dTreeNode = null;
	private LinkedList<Example> trainingSet;
	private final int maxSelectableDepth = 100;
	private final String decisionTreePrefix = "Depth of ";
	
	public DecisionTree() {
		this.trainingSet = new LinkedList<Example>();
	}

	public DecisionTree(LinkedList<Example> trainingSet) {
		this.trainingSet = trainingSet;
		this.runTraining();
	}

	@Override
	public void runTraining() {
		LinkedList<Integer> featureIndexes = this.extractPotentialFeatureIndexes(this.trainingSet.getLast());
		this.dTreeNode = new DTreeNode(this.trainingSet, this.trainingSet, featureIndexes, this.maxDepth);
	}
	
	private LinkedList<Integer> extractPotentialFeatureIndexes(Example example) {
		LinkedList<Integer> returnLL = new LinkedList<Integer>();
		for(int i = 0; i < example.getNumFeatures(); i++) {
			returnLL.add(i);
		}
		return returnLL;
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		this.dTreeNode.runTesting(testSet);
	}

	@Override
	public void setParameter(String parameter) {
		parameter = parameter.replace(this.decisionTreePrefix, UserInterfaceConstants.NULL_STRING);
		this.maxDepth = Integer.parseInt(parameter);
	}

	@Override
	public void runTraining(LinkedList<Example> trainingSet) { 
		this.trainingSet = trainingSet;
		this.runTraining();
	}

	@Override
	public String[] getParameterOptions() {
		int index = 0;
		String[] selectableDepths = new String[this.maxSelectableDepth];
		for(Integer i = 1; i <= this.maxSelectableDepth; i++) {
			selectableDepths[index] = this.decisionTreePrefix + i.toString();
			index++;
		}
		return selectableDepths;
	}

	@Override
	public void wipeValues() { }
}
