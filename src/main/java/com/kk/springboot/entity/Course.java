package com.kk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "course")
/**
 * It's important to note that every @NamedQuery annotation is attached to exactly one entity class or mapped superclass. 
 * But, since the scope of named queries is the entire persistence unit, we should select the query name carefully to avoid a collision.
 * Best way to resolve this challenge by appending class name as prefix. Eg: Course.named_query
 */
//@NamedQuery(name = "Course.select_named_query", query = "select c from Course c")
@NamedQueries(value = {
	@NamedQuery(name = "Course.select_named_query", query = "select c from Course c"),
	@NamedQuery(name = "Course.select_where_questionMarked_named_query", query = "select c from Course c where c.id = ?1"),
	@NamedQuery(name = "Course.select_where_attribute_named_query", query = "select c from Course c where c.id = :id")
})
//@NamedNativeQuery(name = "Course.select_named_native_query", query = "select * from Course")
@NamedNativeQueries(value = {
	@NamedNativeQuery(name = "Course.select_named_native_query", query = "select * from Course"),
	@NamedNativeQuery(name = "Course.select_where_questionMarked_named_native_query", query = "select * from Course where id = ?1"),
	@NamedNativeQuery(name = "Course.select_where_attribute_named_native_query", query = "select * from Course where id = :id")
})
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
