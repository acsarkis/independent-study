package com.utilities.imageProcessing;

import java.util.LinkedList;

import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SpreadsheetBuilder {
	private Workbook workbook;
	private int insertionColumn = 1;
	private int numExamples = -1;
	private boolean featuresInserted = false;
	private CreationHelper createHelper;
	private Sheet sheet;
	private LinkedList<LinkedList<Double>> features = new LinkedList<LinkedList<Double>>();
	private LinkedList<Integer> classification = new LinkedList<Integer>();
	
	public SpreadsheetBuilder(String name) {
		this.workbook = new XSSFWorkbook();
		this.createHelper = workbook.getCreationHelper();
		this.sheet = workbook.createSheet(name);
	}
	
	public void addFeatureColumn(LinkedList<Double> newColumn) {
		if(this.numExamples == -1) {
			this.numExamples = newColumn.size();
		} else if(newColumn.size() != this.numExamples) {
			System.out.println("Error: Inserting feature with invalid number of examples!!");
			return;
		}
		this.features.add(newColumn);
	}
	
	public void addClassificationColumn(LinkedList<Integer> classification) {
		this.classification = classification;
	}
	
	public Workbook build() {
		this.insertionColumn = 0;
		this.insertFeatures();
		this.insertClassifications();
		return this.workbook;
	}
	
	private void insertFeatures() {
		for(int rowNum = 0; rowNum < this.numExamples; rowNum++) {
			Row row = this.sheet.createRow((short)rowNum);
			for(int colNum = 0; colNum < this.features.size(); colNum++) {
				row.createCell(colNum).setCellValue(this.features.get(colNum).get(rowNum));
			}
		}
		
		this.featuresInserted = true;
	}
	
	private void insertClassifications() {
		if(! this.featuresInserted) {
			System.out.println("Warning: you are inserting classifications before insterting features!");
		}
	}
}
