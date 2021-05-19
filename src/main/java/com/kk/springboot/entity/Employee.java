package com.kk.springboot.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
/**
 * In case of @Inheritance(strategy = InheritanceType.SINGLE_TABLE) :
 * EMPLOYEE table will be created with DTYPE, ID, NAME, SALARY, HOURLY_WAGE columns
 * 
 * where 
 * DTYPE : represents type of row entry (i.e. FullTimeEmployee or PartTimeEmployee) because these two are child classes of Employee class.
 * ID, NAME : are columns for Employee class > id and name variables
 * SALARY : column for FullTimeEmployee class > salary variable
 * HOURLY_WAGE : column for PartTimeEmployee class > hourlyWage variable
 */
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false)
	private String name;
	
	public Employee() {
		
	}
	
	public Employee(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + "]";
	}
	
}
