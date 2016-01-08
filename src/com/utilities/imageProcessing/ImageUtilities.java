package com.utilities.imageProcessing;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.utilities.imageProcessing.dataStructures.Vector2D;

public class ImageUtilities {
	public int getAverageColor(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		return (r + g + b) / 3;
	}
	
	public boolean isValidPos(Vector2D position, int width, int height) {
		if(position.getX() >= height || position.getY() >= width || position.getX() < 0 || position.getY() < 0) {
			return false;
		}
		return true;
	}
	
	public int getMinColor(Color color) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		return Math.min(r, Math.min(g, b));
	}
	
	public int getIntFromColor(Color color) {
	    int red = (color.getRed() << 16) & 0x00FF0000; //Shift red 16-bits and mask out other stuff
	    int green = (color.getGreen() << 8) & 0x0000FF00; //Shift Green 8-bits and mask out other stuff
	    int blue = color.getBlue() & 0x000000FF; //Mask out anything not blue.

	    return 0xFF000000 | red | green | blue; //0xFF000000 for 100% Alpha. Bitwise OR everything together.
	}
	
	public int getRedFromInt(int color) {
		return (color & 0x00ff0000) >> 16;
	}
	
	public int getGreenFromInt(int color) {
		return (color & 0x0000ff00) >> 8;
	}
	
	public int getBlueFromInt(int color) {
		return color & 0x000000ff;
	}
}
