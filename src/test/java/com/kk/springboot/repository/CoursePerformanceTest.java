package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kk.springboot.entity.Course;

@SpringBootTest
public class CoursePerformanceTest {
	
	@Autowired
	EntityManager entityManager;
	
	@Test
	@Transactional
	/**
	 * In case, LAZY fetching is used and you want to fetch courses + students, then in this approach
	 * it will perform N+1 hits. 1 hit for fetching all courses and N hits for fetching students data corresponding to course.
	 * 
	 * We can achieve performance by simply making its mapping from LAZY to EAGER but it will be global change, means you want
	 * EAGER fetching only specific areas but we will get it all of the areas.
	 * 
	 * To resolve this, below defined methods are solutions.
	 */
	public void performanceIssue() {
		System.out.println("performanceIssue() >>");
		TypedQuery<Course> courseQuery = entityManager.createQuery("select c from Course c", Course.class);
		List<Course> courseList = courseQuery.getResultList();
		courseList.forEach(course -> System.out.println("Course >> " + course + " :: Student >>" + course.getStudents()));
		assertNotNull(courseList);
	}
	
	@Test
	@Transactional
	/**
	 * Performance issue could be resolved through this approach. In this approach, LEFT OUTER JOIN will be performed 
	 * and all data of courses + students will be fetched in 1 JDBC hit.
	 */
	public void performanceIssueResolvedByEntityGraph() {
		System.out.println("performanceIssueResolvedByEntityGraph() >>");
		
		EntityGraph<Course> entityGraph = entityManager.createEntityGraph(Course.class);
		entityGraph.addSubgraph("students");
		TypedQuery<Course> courseQuery = entityManager.createQuery("select c from Course c", Course.class)
			.setHint("javax.persistence.loadgraph", entityGraph);
		List<Course> courseList = courseQuery.getResultList();
		courseList.forEach(course -> System.out.println("Course >> " + course + " :: Student >>" + course.getStudents()));
		assertNotNull(courseList);
	}
	
	@Test
	@Transactional
	/**
	 * Performance issue could be resolved through this approach. In this approach, INNER JOIN will be performed
	 * and all data of courses + students will be fetched in 1 JDBC hit.
	 */
	public void performanceIssueResolvedByJoinFetch() {
		System.out.println("performanceIssueResolvedByJoinFetch() >>");
		TypedQuery<Course> courseQuery = entityManager.createQuery("select c from Course c JOIN FETCH c.students s", Course.class);
		List<Course> courseList = courseQuery.getResultList();
		courseList.forEach(course -> System.out.println("Course >> " + course + " :: Student >>" + course.getStudents()));
		assertNotNull(courseList);
	}
	
}
