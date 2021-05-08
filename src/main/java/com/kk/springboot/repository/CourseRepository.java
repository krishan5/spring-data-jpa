package com.kk.springboot.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
public class CourseRepository {
	
	@Autowired
	EntityManager entityManager;
	
	public Course findById(long id) {
		
		/**
		 * EntityManager find the Course on behalf of given primary key i.e. id
		 */
		return entityManager.find(Course.class, id);
	}
	
}
