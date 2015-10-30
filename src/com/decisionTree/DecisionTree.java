package com.decisionTree;

import java.util.LinkedList;

import com.example.Example;
import com.interfaces.EagerClassifier;

public class DecisionTree implements EagerClassifier {
	private int maxDepth = 1;
	private DTreeNode dTreeNode = null;
	private final LinkedList<Example> trainingSet;

	public DecisionTree(LinkedList<Example> trainingSet) {
		this.getParameters();
		this.trainingSet = trainingSet;
		this.runTraining();
	}
	
	@Override
	public void getParameters() {
		this.maxDepth = 80;
	}

	@Override
	public void runTraining() {
		LinkedList<Integer> featureIndexes = extractPotentialFeatureIndexes(this.trainingSet.getLast());
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
}
