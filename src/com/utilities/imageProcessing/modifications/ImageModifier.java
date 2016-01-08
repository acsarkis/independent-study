package com.utilities.imageProcessing.modifications;

import java.awt.image.BufferedImage;

import com.utilities.imageProcessing.ImageUtilities;

public abstract class ImageModifier implements ImageModifierInterface{
	protected final ImageUtilities imageUtilities = new ImageUtilities();
}
