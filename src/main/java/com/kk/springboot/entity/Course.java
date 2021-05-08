package com.kk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Course {
	
	@Id
	//@GeneratedValue
	private long id;
	
	private String name;
	
	protected Course() {
		
	}
	
	Course(long id, String name) {
		this.id = id;
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
	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + "]";
	}
	
}
