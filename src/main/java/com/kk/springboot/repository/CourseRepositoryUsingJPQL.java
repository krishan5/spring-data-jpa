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
		List<?> courseList = query.getResultList();
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
	
	public void selectNativeQuery() {
		Query query = entityManager.createNativeQuery("Select * from Course", Course.class);
		List<?> courseList = query.getResultList();
		System.out.println("JPQL selectNativeQuery()");
		courseList.forEach(course -> System.out.println(course));
		System.out.println();
	}
	
	public void selectNamedNativeQuery() {
		TypedQuery<Course> query = entityManager.createNamedQuery("Course.select_named_native_query", Course.class);
		List<Course> courseList = query.getResultList();
		System.out.println("JPQL selectNamedNativeQuery()");
		courseList.forEach(course -> System.out.println(course));
		System.out.println();
	}
	
	public void selectWhereNamedNativeQueries() {
		TypedQuery<Course> query1 = entityManager.createNamedQuery("Course.select_where_questionMarked_named_native_query", Course.class);
		query1.setParameter(1, Long.valueOf(3L)); //If I pass simple 3 (primitive int/long value) then it will error. Hence passing Long class.
		List<Course> courseList1 = query1.getResultList();
		System.out.println("JPQL selectWhereNamedNativeQueries() >> select_where_questionMarked_named_native_query");
		courseList1.forEach(c -> System.out.println(c));
		System.out.println();
		
		TypedQuery<Course> query2 = entityManager.createNamedQuery("Course.select_where_attribute_named_native_query", Course.class);
		query2.setParameter("id", Long.valueOf(3L)); //If I pass simple 3 (primitive int/long value) then it will error. Hence passing Long class.
		List<Course> courseList2 = query2.getResultList();
		System.out.println("JPQL selectWhereNamedNativeQueries() >> select_where_attribute_named_native_query");
		courseList2.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectCoursesWithoutStudent() {
		TypedQuery<Course> courseListWithoutStudentQuery = entityManager.createQuery("select c from Course c where c.students is empty", Course.class);
		List<Course> courseListWithoutStudent = courseListWithoutStudentQuery.getResultList();
		System.out.println("JPQL selectCoursesWithoutStudent()");
		courseListWithoutStudent.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectCoursesWithAtleastTwoStudent() {
		TypedQuery<Course> courseListWithAtleastTwoStudentQuery = entityManager.createQuery("select c from Course c where size(c.students) >= 2", Course.class);
		List<Course> courseListWithAtleastTwoStudent = courseListWithAtleastTwoStudentQuery.getResultList();
		System.out.println("JPQL selectCoursesWithAtleastTwoStudent()");
		courseListWithAtleastTwoStudent.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	public void selectCoursesOrderedByStudentSize() {
		TypedQuery<Course> courseListOrderedByStudentSizeQuery = entityManager.createQuery("select c from Course c order by size(c.students) desc", Course.class);
		List<Course> courseListOrderedByStudentSize = courseListOrderedByStudentSizeQuery.getResultList();
		System.out.println("JPQL selectCoursesOrderedByStudentSize()");
		courseListOrderedByStudentSize.forEach(c -> System.out.println(c));
		System.out.println();
	}
	
	/**
	 * Three types of JOIN :
	 * 1. JOIN : Join Course and Student tables. If course is not linked with any student then that course will not be fetched.
	 * 2. LEFT JOIN : Join Course and Student tables. Also return course which is not linked with any student (as NULL).
	 * 3. CROSS JOIN : Join Course and Student tables. It return all possible combination of course (n records) and student (m records). n * m records will be fetched.
	 */
	public void selectCoursesJoinStudent() {
		Query coursesJoinStudentQuery = entityManager.createQuery("select c, s from Course c JOIN c.students s");
		List<Object[]> resultSet = coursesJoinStudentQuery.getResultList();
		System.out.println("JPQL selectCoursesJoinStudent()");
		for(Object[] result : resultSet) {
			System.out.println("Course : " + result[0]);
			System.out.println("Student : " + result[1]);
		}
		System.out.println();
	}
	
	/**
	 * Three types of JOIN :
	 * 1. JOIN : Join Course and Student tables. If course is not linked with any student then that course will not be fetched.
	 * 2. LEFT JOIN : Join Course and Student tables. Also return course which is not linked with any student (as NULL).
	 * 3. CROSS JOIN : Join Course and Student tables. It return all possible combination of course (n records) and student (m records). n * m records will be fetched.
	 */
	public void selectCoursesLeftJoinStudent() {
		Query coursesLeftJoinStudentQuery = entityManager.createQuery("select c, s from Course c LEFT JOIN c.students s");
		List<Object[]> resultSet = coursesLeftJoinStudentQuery.getResultList();
		System.out.println("JPQL selectCoursesLeftJoinStudent()");
		for(Object[] result : resultSet) {
			System.out.println("Course : " + result[0]);
			System.out.println("Student : " + result[1]);
		}
		System.out.println();
	}
	
	/**
	 * Three types of JOIN :
	 * 1. JOIN : Join Course and Student tables. If course is not linked with any student then that course will not be fetched.
	 * 2. LEFT JOIN : Join Course and Student tables. Also return course which is not linked with any student (as NULL).
	 * 3. CROSS JOIN : Join Course and Student tables. It return all possible combination of course (n records) and student (m records). n * m records will be fetched.
	 */
	public void selectCoursesCrossJoinStudent() {
		Query coursesCrossJoinStudentQuery = entityManager.createQuery("select c, s from Course c, Student s");
		List<Object[]> resultSet = coursesCrossJoinStudentQuery.getResultList();
		System.out.println("JPQL selectCoursesCrossJoinStudent()");
		for(Object[] result : resultSet) {
			System.out.println("Course : " + result[0] + " ::::: Student : " + result[1]);
		}
		System.out.println();
	}
	
}
