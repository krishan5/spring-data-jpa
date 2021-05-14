package com.kk.springboot.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Student;

@Repository
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

}
