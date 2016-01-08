package com.mvc.view.subviews;

import java.awt.Dimension;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import javax.swing.JComboBox;

import com.mvc.view.subviews.UserInterfaceConstants;


public class TextDropDown<T> extends JComboBox<String> {
	private static final String CATOGORY1 = "Category1";
	private static final String CATOGORY2 = "Category2";
	private static final String CATOGORY3 = "Category3";
	private HashMap<String, String[]> aggregationCategories;
	
	public TextDropDown() {
		super(new String[]{CATOGORY1, CATOGORY2, CATOGORY3});
		this.setPreferredSize(new Dimension(150, 50));
	}
	
	public TextDropDown(String[] inputLabels, Boolean defaultIsNull) {
		if(defaultIsNull) {
			this.addItem(UserInterfaceConstants.NULL_STRING);
		}
		for(String label : inputLabels) {
			this.addItem(label);
		}
	}
	
	public TextDropDown(HashMap<String, String[] > aggregationCategories) {
		this.aggregationCategories = aggregationCategories;
	}
	
	public void aggregate(String input) {
		this.removeAllItems();
		if(this.aggregationCategories.containsKey(input)) {
			for(String category : this.aggregationCategories.get(input)) {
				this.addItem(category);
			}
		}
	}
	
	/* Use a base directory to extract all of the categories below it  */
	// NOTE: This method only works in Eclipse.  It will not work in .jar files!
	public TextDropDown(final String baseDirectory) {
		java.net.URL baseDirectoryURL  = TextDropDown.class.getResource(baseDirectory);
		try {
			File dir = new File(baseDirectoryURL.toURI());
			for(File folder : dir.listFiles()) {
				URI base = URI.create(baseDirectoryURL.toString());
				URI absolute = folder.toURI();
				URI relativePath = base.relativize(absolute);
				this.addItem(baseDirectory + relativePath.toString());
			}
		} catch (URISyntaxException e) {
			
		}
	}
	
	@SuppressWarnings("unchecked")
	public T getSelectedItem() {
		return (T) super.getSelectedItem();
	}
}
