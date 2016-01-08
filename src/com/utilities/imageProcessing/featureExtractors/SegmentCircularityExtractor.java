package com.utilities.imageProcessing.featureExtractors;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class SegmentCircularityExtractor implements FeatureExtractor {
	private Color segmentColor;
	
	public SegmentCircularityExtractor(Color segmentColor) {
		this.segmentColor = segmentColor;
	}

	@Override
	public double extractFeature(BufferedImage image) {
		return 22;
	}

}
