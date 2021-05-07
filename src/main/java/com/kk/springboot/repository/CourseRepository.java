package com.kk.springboot.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
public class CourseRepository {
	
	@PersistenceContext
	EntityManager entityManager;
	
	public Course findById(long id) {
		
		/**
		 * EntityManager find the Course on behalf of given primary key i.e. id
		 */
		return entityManager.find(Course.class, id);
	}
	
}
