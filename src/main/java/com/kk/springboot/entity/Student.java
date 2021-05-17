package com.kk.springboot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	/**
	 * By @OneToOne, PASSPORT_ID column will be added in STUDENT table along with foreign key.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	private Passport passport;
	
	/**
	 * Explanation of @ManyToMany is explained in Course class.
	 */
	@ManyToMany
	private List<Course> courses;
	
	public Student() {
		
	}

	public Student(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Passport getPassport() {
		return passport;
	}
	public void setPassport(Passport passport) {
		this.passport = passport;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public List<Course> getCourses() {
		return courses;
	}
	public void addCourse(Course course) {
		if(this.courses != null)
			courses = new ArrayList<Course>();
		courses.add(course);
	}
	public void removeCourse(Course course) {
		courses.remove(course);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", passport=" + passport.getId() + "]";
	}
	
}
