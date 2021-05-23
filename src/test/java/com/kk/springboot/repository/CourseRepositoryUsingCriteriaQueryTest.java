package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kk.springboot.entity.Course;

@SpringBootTest
public class CourseRepositoryUsingCriteriaQueryTest {
	
	@Autowired
	CourseRepositoryUsingCriteriaQuery courseCriteriaQueryRepo;
	
	@Test
	public void testSelect() {
		List<Course> courseList = courseCriteriaQueryRepo.select();
		System.out.println("testSelect() >> courseList.size() >> " + courseList.size());
		assertTrue(courseList.size() > 0);
	}
	
	@Test
	public void testSelectCourseWhereLike() {
		List<Course> courseList = courseCriteriaQueryRepo.selectCourseWhereLike();
		System.out.println("testSelectCourseWhereLike() >> courseList.size() >> " + courseList.size());
		assertTrue(courseList.size() > 0);
	}
	
	@Test
	public void testSelectCourseHasNoStudent() {
		List<Course> courseList = courseCriteriaQueryRepo.selectCourseHasNoStudent();
		System.out.println("testSelectCourseHasNoStudent() >> courseList.size() >> " + courseList.size());
		assertTrue(courseList.size() > 0);
	}
	
	@Test
	public void testSelectCourseJoinStudent() {
		List<Course> courseList = courseCriteriaQueryRepo.selectCourseJoinStudent();
		System.out.println("testSelectCourseJoinStudent() >> courseList.size() >> " + courseList.size());
		assertTrue(courseList.size() > 0);
	}
	
	@Test
	public void testSelectCourseLeftJoinStudent() {
		List<Course> courseList = courseCriteriaQueryRepo.selectCourseLeftJoinStudent();
		System.out.println("testSelectCourseLeftJoinStudent() >> courseList.size() >> " + courseList.size());
		assertTrue(courseList.size() > 0);
	}
	
}
