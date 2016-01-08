package com.utilities.imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.imageio.ImageIO;

import com.utilities.imageProcessing.modifications.ToBinaryImageModification;
import com.utilities.imageProcessing.featureExtractors.ImageSegmentExtractor;
import com.utilities.imageProcessing.featureExtractors.PixelValueExtractor;
import com.utilities.imageProcessing.featureExtractors.SegmentCircularityExtractor;
import com.utilities.imageProcessing.modifications.BoundingBoxModification;
import com.utilities.imageProcessing.modifications.CropModification;
import com.utilities.imageProcessing.modifications.ResizeModifiction;
import com.utilities.imageProcessing.modifications.SaveImageModification;

public class ImageDataExtractor {
	private String imagesLocation;
	private String outputSpreadsheetLocation;
	private final SpreadsheetBuilder spreadsheetBuilder;
	private ImageProcessor imageProcessor = new ImageProcessor();
	private final int desiredWidth = 16;
	private final int desiredHeight = 16;
	private final String outputWorkbookName = "generatedSpreadsheet.xlsx";
	private final String outputSheetName = "Sheet1";
	
	public ImageDataExtractor(final String imagesLocation, final String outputSpreadsheetLocation) {
		this.imagesLocation = imagesLocation;
		this.outputSpreadsheetLocation = outputSpreadsheetLocation;
		this.spreadsheetBuilder = new SpreadsheetBuilder(this.outputSheetName);
		
		ArrayList<BufferedImage> images = this.imageProcessor.preProcessImages(imagesLocation, this.desiredWidth, this.desiredHeight);
		this.imageProcessor.applyToArraylist(images, new SaveImageModification(this.imagesLocation));
		this.createWorkbook(this.outputSpreadsheetLocation, images);
	}
	
	private void createWorkbook(final String location, final ArrayList<BufferedImage> images) {
		this.extractPixelFeatures(images);
		this.extractCircularityScoreFeatures(images);
		ImageSegmentExtractor imageSegmentExtractor = new ImageSegmentExtractor();
		//imageSegmentExtractor.extractFeature(images.get(0));
		this.imageProcessor.extractFeatureFromImages(images, new ImageSegmentExtractor());
		
		this.saveWorkbook(this.spreadsheetBuilder.build(), location);
	}
	
	private void extractCircularityScoreFeatures(final ArrayList<BufferedImage> images) {
		LinkedList<Double> circularityFeatures = this.imageProcessor.extractFeatureFromImages(images, new SegmentCircularityExtractor(Color.BLACK));
		this.spreadsheetBuilder.addFeatureColumn(circularityFeatures);
	}
	
	/* Extracts the 0 or 1 for every image in the spreadsheet */
	private void extractPixelFeatures(final ArrayList<BufferedImage> images) {
		BufferedImage image = images.get(0);
		
		for(int x = 0; x < image.getWidth(); x++) {
			for(int y = 0; y < image.getHeight(); y++) {
				LinkedList<Double> features = this.imageProcessor.extractFeatureFromImages(images, new PixelValueExtractor(x,y));
				this.spreadsheetBuilder.addFeatureColumn(features);
			}
		}
	}
	
	private void saveWorkbook(Workbook workbook, final String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path + this.outputWorkbookName);
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
