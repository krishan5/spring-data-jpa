package com.kk.springboot.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
/**
 * In SQL >> select * from Course
 * In JPQL >> select c from Course c
 */
public class CourseRepositoryUsingJPQL {
	
	@Autowired
	EntityManager entityManager;
	
	public void select() {
		List courseList = entityManager.createQuery("select c from Course c").getResultList();
		System.out.println("JPQL select()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectByType() {
		List<Course> courseList = entityManager.createQuery("select c from Course c", Course.class).getResultList();
		System.out.println("JPQL selectByType()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectByTypeAndWhere() {
		List<Course> courseList = entityManager.createQuery("select c from Course c where name like '%updated%'", Course.class).getResultList();
		System.out.println("JPQL selectByTypeAndWhere()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
}
