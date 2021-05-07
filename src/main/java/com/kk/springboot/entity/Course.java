package com.kk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Course {
	
	@Id
	@GeneratedValue
	private long id;
	
	private String name;
	
	protected Course() {
		
	}
	
	Course(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
}
