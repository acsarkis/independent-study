package com.utilities.imageProcessing.modifications;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class ResizeModifiction extends ImageModifier {
	private final int width;
	private final int height;
	
	
	public ResizeModifiction(int height, int width) {
		this.width = width;
		this.height = height;
	}

	@Override
	public BufferedImage applyModificationToImage(BufferedImage image) {
		BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		// Paint scaled version of image to new image
		Graphics2D graphics2D = scaledImage.createGraphics();
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2D.drawImage(image, 0, 0, width, height, null);
		// clean up
		graphics2D.dispose();
		
		return scaledImage;
	}
}
