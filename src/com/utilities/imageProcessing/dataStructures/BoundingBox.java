package com.utilities.imageProcessing.dataStructures;

public class BoundingBox {
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public BoundingBox(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public BoundingBox(Vector2D vec1, Vector2D vec2) {
		this.x1 = vec1.getX();
		this.y1 = vec1.getY();
		this.x2 = vec2.getX();
		this.y2 = vec2.getY();
	}
	
	public void setX1(int x1) {
		this.x1 = x1;
	}
	
	public int getX1() {
		return this.x1;
	}
	
	public void setY1(int y1) {
		this.y1 = y1;
	}
	
	public int getY1() {
		return this.y1;
	}
	
	public void setX2(int x2) {
		this.x2 = x2;
	}
	
	public int getX2() {
		return this.x2;
	}
	
	public void setY2(int y2) {
		this.y2 = y2;
	}
	
	public int getY2() {
		return this.y2;
	}
	
	public int[] getAllFourValues() {
		int[] values = {x1, y1, x2, y2};
		return values;
	}
	
	public String toString() {
		return "From: " + new Vector2D(x1,y1).toString() + " To: " + new Vector2D(x2,y2).toString();
	}
}
