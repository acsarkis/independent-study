package com.driver;

import com.mvc.view.subviews.MainWindow;

public class Main {
	public static void main(String[] args) {
		// modify this boolean to run headless or not
		boolean runHeadless = true;
		if(runHeadless) {
			HeadlessModeDriver driver = new HeadlessModeDriver();
		} else {
			MainWindow mainWindow = new MainWindow();
		}
	}
}
