package com.kk.springboot.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;
import com.kk.springboot.entity.Passport;
import com.kk.springboot.entity.Student;

@Repository
@Transactional
public class StudentRepository {
	
	@Autowired
	EntityManager entityManager;
	
	public Student findById(long id) {
		/**
		 * If no fetchtype set on @OneToOne mapping then it will fetch EAGER by default.
		 * Which means hibernate will perform join operation between STUDENT and PASSPORT
		 * tables to fetch data.
		 * 
		 * If FetchType is set as LAZY then it will only fetch STUDENT data.
		 * There will be no DB hit on PASSPORT table because we are not executing student.getPassport()
		 * in that transition.
		 */
		return entityManager.find(Student.class, id);
	}
	
	public Student save(Student student) {
		if(student.getId() == 0L)
			entityManager.persist(student);
		else
			entityManager.merge(student);
		return student;
	}
	
	public void deleteById(long id) {
		Student student = findById(id);
		entityManager.remove(student);
	}
	
	public Student saveStudentWithPassport(Student student, Passport passport) {
		/**
		 * Don't persist student directly without persisting passport before.
		 * 
		 * As student is the owning side of passport i.e. it requires passport_id before saving,
		 * then persist the passport first and then persist the student.
		 */
		entityManager.persist(passport);
		
		student.setPassport(passport);
		entityManager.persist(student);
		return student;
	}
	
	public Student findStudentWithPassport(long studentId) {
		/**
		 * If no fetchtype set on @OneToOne mapping then it will fetch EAGER by default.
		 * Which means hibernate will perform join operation between STUDENT and PASSPORT
		 * tables to fetch data.
		 * 
		 * If FetchType is set as LAZY then it will only fetch STUDENT data.
		 * When student.getPassport() will be executed then DB hit will be done on PASSPORT table instantly
		 * to get passport when we demand for it.
		 * 
		 * NOTICE : No join operation will be performed.
		 * To fetch Student data, normal select query with WHERE condition will be executed on STUDENT table.
		 * Same for passport, normal select query with WHERE condition will be executed on PASSPORT table.
		 */
		Student student = entityManager.find(Student.class, studentId);
		
		/**
		 * Here select query will be executed as explained above.
		 * Hibernate knows Passport details are not fetched yet that's why it fetch its data in lazy mode.
		 */
		student.getPassport();
		
		return student;
	}
	
	public Student findStudentUsingPassportOnBehalfOfBidirectional(long passportId) {
		/**
		 * We are able fetch student object on behalf of passport id just because of Bidirectional @OneToOne used 
		 * which means @OneToOne annotation on both classes i.e. Student and Passport.
		 * and both classes containing each other variable :
		 * Student class has passport variable and Passport class has student variable.
		 * 
		 * By doing this PASSPORT_ID column will be added in STUDENT table and STUDENT_ID column in PASSPORT table.
		 * 
		 * But we don't need this, we only want PASSPORT_ID column in STUDENT table. To do this, "mappedBy"
		 * attribute is added in Passport class like :
		 * @OneToOne(fetch = FetchType.LAZY, mappedBy = "passport")
		 */
		Passport passport = entityManager.find(Passport.class, passportId);
		Student student = passport.getStudent();
		return student;
	}
	
	public Student findStudentAndItsCourses(long studentId) {
		Student student = entityManager.find(Student.class, studentId);
		return student;
	}
	
	public void insertStudentAndItsCourses(Student student, List<Course> courseList) {
		entityManager.persist(student);
		courseList.forEach(course -> {
			student.addCourse(course);
			course.addStudent(student);
			entityManager.persist(course);
		});
	}

}
