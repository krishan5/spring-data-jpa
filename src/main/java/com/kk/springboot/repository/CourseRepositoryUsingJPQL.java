package com.kk.springboot.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
@Transactional
/**
 * In SQL >> select * from Course
 * In JPQL >> select c from Course c
 */
public class CourseRepositoryUsingJPQL {
	
	@Autowired
	private EntityManager entityManager;
	
	public void select() {
		Query query = entityManager.createQuery("select c from Course c");
		List courseList = query.getResultList();
		System.out.println("JPQL select()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectByType() {
		TypedQuery<Course> query = entityManager.createQuery("select c from Course c", Course.class);
		List<Course> courseList = query.getResultList();
		System.out.println("JPQL selectByType()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectByTypeAndWhere() {
		TypedQuery<Course> query = entityManager.createQuery("select c from Course c where name like '%updated%'", Course.class);
		List<Course> courseList = query.getResultList();
		System.out.println("JPQL selectByTypeAndWhere()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectNamedQuery() {
		TypedQuery<Course> query = entityManager.createNamedQuery("Course.select_named_query", Course.class);
		List<Course> courseList = query.getResultList();
		System.out.println("JPQL namedQuery()");
		courseList.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectWhereNamedQueries() {
		TypedQuery<Course> query1 = entityManager.createNamedQuery("Course.select_where_questionMarked_named_query", Course.class);
		query1.setParameter(1, Long.valueOf(3L)); //If I pass simple 3 (primitive int/long value) then it will error. Hence passing Long class.
		List<Course> courseList1 = query1.getResultList();
		System.out.println("JPQL selectWhereNamedQueries() >> select_where_questionMarked_named_query");
		courseList1.forEach(c -> System.out.println(c));
		System.out.println();
		
		TypedQuery<Course> query2 = entityManager.createNamedQuery("Course.select_where_attribute_named_query", Course.class);
		query2.setParameter("id", Long.valueOf(3L)); //If I pass simple 3 (primitive int/long value) then it will error. Hence passing Long class.
		List<Course> courseList2 = query2.getResultList();
		System.out.println("JPQL selectWhereNamedQueries() >> select_where_attribute_named_query");
		courseList2.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
}
