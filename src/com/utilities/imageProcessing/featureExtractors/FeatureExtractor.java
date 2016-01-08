package com.utilities.imageProcessing.featureExtractors;

import java.awt.image.BufferedImage;

public interface FeatureExtractor {
	public double extractFeature(BufferedImage image);
}
