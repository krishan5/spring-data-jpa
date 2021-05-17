package com.kk.springboot.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
	
	//By default fetch strategy is LAZY for @OneToMany relation
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "course")
	private List<Review> reviews;
	
	/**
	 * It will create new table in DB with name : COURSE_STUDENTS table having COURSE_ID and STUDENTS_ID columns
	 * to support ManyToMany relation between each other
	 * where left side of table name COURSE_STUDENTS is this class name and right side of table name is variable name in this class i.e. students.
	 * In case we support bidirectional relation of it i.e. @ManyToMany in Student class on List<Course>, then 
	 * one more table will be created with name : STUDENT_COURSES table having STUDENT_ID and COURSES_ID columns.
	 * 
	 * Due to having @ManyToMany annotation on both classes, it will create two tables for us.
	 * But we need only 1 of it.
	 */
	@ManyToMany
	private List<Student> students;
	
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
	
	public List<Review> getReviews() {
		return this.reviews;
	}
	public void addReview(Review review) {
		if(this.reviews == null)
			reviews = new ArrayList<Review>();
		reviews.add(review);
	}
	public void removeReview(Review review) {
		reviews.remove(review);
	}
	
	public List<Student> getStudents() {
		return students;
	}
	public void addStudent(Student student) {
		if(this.students == null)
			students = new ArrayList<Student>();
		students.add(student);
	}
	public void removeStudent(Student student) {
		students.remove(student);
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + "]";
	}
	
}
