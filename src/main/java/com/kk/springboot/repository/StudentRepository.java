package com.kk.springboot.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
	
	public Student findStudentWithPassport(long id) {
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
		Student student = entityManager.find(Student.class, id);
		
		/**
		 * Here select query will be executed as explained above.
		 */
		student.getPassport();
		
		return student;
	}

}
