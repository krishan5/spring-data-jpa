package com.kk.springboot.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
@Transactional
public class CourseRepositoryUsingCriteriaQuery {
	
	@Autowired
	EntityManager entityManager;
	
	/**
	 * Following are steps to create criteria query :
	 * 1. Use Criteria Builder to create a Criteria Query returning the expected result object.
	 * 2. Define roots for tables which are involved in the query.
	 * 3. Define predicates etc using Criteria Builder.
	 * 4. Add predicates to Criteria Query.
	 * 5. Build the TypedQuery using the EntityManager and CriteriaQuery.
	 */
	public List<Course> select() {
		//select c from Course c"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); //select c
		
		// 2. Define roots for tables which are involved in the query.
		Root<Course> courseFromRoot = cq.from(Course.class); //from Course
		
		// 3. Define predicates etc using Criteria Builder.
		
		// 4. Add predicates to Criteria Query.
		
		// 5. Build the TypedQuery using the EntityManager and CriteriaQuery.
		TypedQuery<Course> courseQuery = entityManager.createQuery(cq.select(courseFromRoot));
		List<Course> courseList = courseQuery.getResultList();
		return courseList;
	}
	
	public List<Course> selectCourseWhereLike() {
		//select c from Course c where c.name like "%update%"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); //select c
		
		// 2. Define roots for tables which are involved in the query.
		Root<Course> courseFromRoot = cq.from(Course.class); //from Course
		
		// 3. Define predicates etc using Criteria Builder.
		Predicate likeUpdatedCourse = cb.like(courseFromRoot.get("name"), "%update%"); //c.name like "%update%"
		
		// 4. Add predicates to Criteria Query.
		cq.where(likeUpdatedCourse); //where ...
		
		// 5. Build the TypedQuery using the EntityManager and CriteriaQuery.
		TypedQuery<Course> courseQuery = entityManager.createQuery(cq.select(courseFromRoot));
		List<Course> courseList = courseQuery.getResultList();
		return courseList;
	}
	
	public List<Course> selectCourseHasNoStudent() {
		//select c from Course c where c.students is empty"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); //select c
		
		// 2. Define roots for tables which are involved in the query.
		Root<Course> courseFromRoot = cq.from(Course.class); //from Course
		
		// 3. Define predicates etc using Criteria Builder.
		Predicate courseStudentIsEmpty = cb.isEmpty(courseFromRoot.get("students"));
		
		// 4. Add predicates to Criteria Query.
		cq.where(courseStudentIsEmpty); //where ...
		
		// 5. Build the TypedQuery using the EntityManager and CriteriaQuery.
		TypedQuery<Course> courseQuery = entityManager.createQuery(cq.select(courseFromRoot));
		List<Course> courseList = courseQuery.getResultList();
		return courseList;
	}
	
	public List<Course> selectCourseJoinStudent() {
		//select c from Course c JOIN c.students"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); //select c
		
		// 2. Define roots for tables which are involved in the query.
		Root<Course> courseFromRoot = cq.from(Course.class); //from Course
		
		// 3. Define predicates etc using Criteria Builder.
		courseFromRoot.join("students"); //This will perform inner join
		
		// 4. Add predicates to Criteria Query.
		
		// 5. Build the TypedQuery using the EntityManager and CriteriaQuery.
		TypedQuery<Course> courseQuery = entityManager.createQuery(cq.select(courseFromRoot));
		List<Course> courseList = courseQuery.getResultList();
		return courseList;
	}
	
	public List<Course> selectCourseLeftJoinStudent() {
		//select c from Course c JOIN c.students"
		
		//1. Use Criteria Builder to create a Criteria Query returning the expected result object.
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Course> cq = cb.createQuery(Course.class); //select c
		
		// 2. Define roots for tables which are involved in the query.
		Root<Course> courseFromRoot = cq.from(Course.class); //from Course
		
		// 3. Define predicates etc using Criteria Builder.
		courseFromRoot.join("students", JoinType.LEFT); //This will perform left join
		
		// 4. Add predicates to Criteria Query.
		
		// 5. Build the TypedQuery using the EntityManager and CriteriaQuery.
		TypedQuery<Course> courseQuery = entityManager.createQuery(cq.select(courseFromRoot));
		List<Course> courseList = courseQuery.getResultList();
		return courseList;
	}
	
}
