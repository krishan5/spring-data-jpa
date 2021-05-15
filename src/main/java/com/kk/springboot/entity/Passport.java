package com.kk.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Passport {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String number;
	
	/**
	 * By adding @OneToOne annotation on both classes i.e. Student and Passport.
	 * and both classes containing each other variable :
	 * Student class has passport variable and Passport class has student variable.
	 * 
	 * By doing this PASSPORT_ID column will be added in STUDENT table and STUDENT_ID column in PASSPORT table.
	 * 
	 * But we don't need this, we only want PASSPORT_ID column in STUDENT table. To do this, "mappedBy"
	 * attribute is added in Passport class like :
	 * @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
	 * 
	 * where "passport" is variable name of Passport type present in Student class (owning side of mapping column). 
	 */
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
	private Student student;
	
	public Passport() {
		
	}
	
	public Passport(String number) {
		this.number = number;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}

	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
}
