package com.kk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/**
	 * In case of : strategy = GenerationType.AUTO
	 * I was facing exception :
	 * if schema.sql provided >> could not prepare statement; SQL [call next value for hibernate_sequence];
	 * if schema.sql not provided >> NULL not allowed for column "ID"; SQL statement:
	 * 
	 * By replacing it with GenerationType.IDENTITY, it resolves.
	 * Why this happened ? Don't know yet.
	 */
	private long id;
	
	private String name;
	
	protected Course() {
		
	}
	
	public Course(String name) {
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
