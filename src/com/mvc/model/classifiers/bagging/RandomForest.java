package com.mvc.model.classifiers.bagging;

import java.util.LinkedList;

import com.example.Example;
import com.mvc.model.EagerClassifier;
import com.mvc.model.classifiers.decisionTree.DecisionTree;
import com.mvc.view.subviews.UserInterfaceConstants;

public class RandomForest extends BootStrapper implements EagerClassifier {
	private LinkedList<Example> trainingSet;
	private final int minNumberOfTrees = 2;
	private final int maxNumberOfTrees = 100;
	private int selectedNumberOfTrees = 70;
	private BootstrapAggregator bootstrapAggregator = new BootstrapAggregator();
	private final String randomForestPrefix = "# of Trees: ";
	
	public RandomForest(LinkedList<Example> trainingSet) {
		this.trainingSet = trainingSet;
		this.runTraining();
	}
	
	public RandomForest() {
		this.trainingSet = new LinkedList<Example>();
	}

	@Override
	public void setParameter(String parameter) { 
		parameter = parameter.replace(this.randomForestPrefix, UserInterfaceConstants.NULL_STRING);
		this.selectedNumberOfTrees = Integer.parseInt(parameter);
	}

	@Override
	public void runTraining() {
		if(this.selectedNumberOfTrees > this.trainingSet.size()) {
			return;
		}
		LinkedList<LinkedList<Example> > trainingSetPartitions = this.randomlyPartitionData(this.trainingSet, this.selectedNumberOfTrees);
		
		for(int i = 0; i < trainingSetPartitions.size(); i++) {
			System.out.println("-- Random Forest: Training tree! " + (i + 1) + " --");
			DecisionTree currentDecisionTree = new DecisionTree();
			currentDecisionTree.setParameter("40");
			currentDecisionTree.runTraining(trainingSetPartitions.get(i));
			bootstrapAggregator.addClassifier(currentDecisionTree);
		}
	}

	@Override
	public void runTesting(LinkedList<Example> testSet) {
		this.bootstrapAggregator.getClassificationOnTestSet(testSet);
	}

	@Override
	public void runTraining(LinkedList<Example> trainingSet) {
		this.trainingSet = trainingSet;
		
		if(this.selectedNumberOfTrees > this.trainingSet.size()) {
			return;
		}
		LinkedList<LinkedList<Example> > trainingSetPartitions = this.randomlyPartitionData(this.trainingSet, this.selectedNumberOfTrees);
		
		for(int i = 0; i < trainingSetPartitions.size(); i++) {
			System.out.println("-- Random Forest: Training tree! " + (i + 1) + " --");
			DecisionTree currentDecisionTree = new DecisionTree();
			currentDecisionTree.setParameter("40");
			currentDecisionTree.runTraining(trainingSetPartitions.get(i));
			bootstrapAggregator.addClassifier(currentDecisionTree);
		}
	}

	@Override
	public String[] getParameterOptions() {
		int index = 0;
		String[] selectableOptions = new String[(this.maxNumberOfTrees - this.minNumberOfTrees) + 1];
		for(int i = this.minNumberOfTrees; i <= this.maxNumberOfTrees; i++) {
			selectableOptions[index] = this.randomForestPrefix + Integer.toString(i);
			index++;
		}
		return selectableOptions;
	}

	@Override
	public void wipeValues() {
		this.bootstrapAggregator = new BootstrapAggregator();
	}
	
}
