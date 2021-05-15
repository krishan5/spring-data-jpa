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

}
