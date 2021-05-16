package com.kk.springboot.entity;

public enum ReviewRating {
	
	BAD(1),
	NORMAL(2),
	AVERAGE(3),
	GOOD(4),
	VERY_GOOD(5);
	
	int value;
	
	private ReviewRating(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}

}
