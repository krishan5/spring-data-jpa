package com.kk.springboot.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

//@Entity
/**
 * Separate table will be created for concrete child classes :
 * FULL_TIME_EMPLOYEE and PART_TIME_EMPLOYEE two tables will be created exact like in way of InheritanceType.TABLE_PER_CLASS.
 * 
 * Columns will be ID, NAME, SALARY in FULL_TIME_EMPLOYEE table.
 * Columns will be ID, NAME, HOURLY_WAGE in PART_TIME_EMPLOYEE table.
 * 
 * Explanation : This (Employee) class is like common definition, other that that there is no relationship between
 * subclasses. Separate tables will be created. So there is no Employee relation i.e. no inheritance hierarchy at all.
 * MappedSuperclass completely eliminates the inheritance hierarchy between them. This is us who are defining common 
 * things in this class which will be used in child classes tables, other than that there is no relationship between them.
 * 
 * In case we try to fetch details on behalf of Employee entity instead of FullTimeEmployee or PartTimeEmployee entity
 * while fetching/saving operation we will face following exceptions :
 * JPA Eg: entityManager.createQuery("select e from Employee e", Employee.class); // Exception >> Employee entity is not mapped 
 * Hibernate Eg: entityManager.find(Employee.class, id); //Exception >> Unable to locate persister: com.kk.springboot.entity.Employee
 * 
 * If we use it along with @Entity annotation then we will face this exception :
 * An entity cannot be annotated with both @Entity and @MappedSuperclass
 */
@MappedSuperclass
/**
 * In case of @Inheritance(strategy = InheritanceType.SINGLE_TABLE) :
 * Definition : A single table per class hierarchy.
 * EMPLOYEE table will be created with DTYPE, ID, NAME, SALARY, HOURLY_WAGE columns
 * 
 * where 
 * DTYPE : It is called Discriminator column which represents type of row entry 
 * (i.e. FullTimeEmployee or PartTimeEmployee) because these two are child classes of Employee class.
 * ID, NAME : are columns for Employee class > id and name variables
 * SALARY : column for FullTimeEmployee class > salary variable
 * HOURLY_WAGE : column for PartTimeEmployee class > hourlyWage variable
 */
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "EMPLOYEE_TYPE") //It renames DTYPE to EMPLOYEE_TYPE column

/**
 * In case of @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) :
 * FULL_TIME_EMPLOYEE and PART_TIME_EMPLOYEE these two tables will be created.
 * In short a table per concrete class will be created and we have FullTimeEmployee and PartTimeEmployee are the only concrete classes.
 * 
 * Columns will be ID, NAME, SALARY in FULL_TIME_EMPLOYEE table.
 * Columns will be ID, NAME, HOURLY_WAGE in PART_TIME_EMPLOYEE table.
 * 
 * Notice : No DTYPE is there just because tables are already divided as per type and 
 * we don't need this column to identify which row entry is of which employee type.
 */
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

/**
 * In case of @Inheritance(strategy = InheritanceType.JOINED) :
 * EMPLOYEE, FULL_TIME_EMPLOYEE and PART_TIME_EMPLOYEE these three tables will be created.
 * 
 * Columns will be ID, NAME in EMPLOYEE table.
 * Columns will be SALARY, ID in FULL_TIME_EMPLOYEE table.
 * Columns will be HOURLY_WAGE, ID in PART_TIME_EMPLOYEE table.
 * 
 * Notice : ID column is part of both concrete classes so that it perform JOIN operation.
 */
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Employee {
	
	@Id
	/**
	 * In case of @Inheritance(strategy = InheritanceType.SINGLE_TABLE) :
	 * We can use @GeneratedValue(strategy = GenerationType.INDENTITY or AUTO)
	 * because it that case we have single table where ID will be incremented automatically.
	 * 
	 * In case of @Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) :
	 * If we use @GeneratedValue(strategy = GenerationType.INDENTITY), then following exception will be thrown :
	 * Cannot use identity column key generation with <union-subclass> mapping.
	 * So the preferred option is AUTO.
	 */
	@GeneratedValue(strategy = GenerationType.AUTO)
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
