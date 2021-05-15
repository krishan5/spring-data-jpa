package com.kk.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
}
