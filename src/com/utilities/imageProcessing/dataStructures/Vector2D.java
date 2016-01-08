package com.utilities.imageProcessing.dataStructures;

public class Vector2D {
	private int x;
	private int y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Vector2D clone() {
		return new Vector2D(this.x, this.y);
	}
	
	public boolean equals(Vector2D input) {
		if(this.x == input.getX() && this.y == input.getY()) {
			return true;
		}
		return false;
	}
	
	public String toString() {
		return "< " + x + ", " + y + " >";
	}
}
