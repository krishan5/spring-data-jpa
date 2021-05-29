package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.kk.springboot.entity.Course;

@SpringBootTest
public class CourseSpringDataRepositoryTest {
	
	@Autowired
	CourseSpringDataRepository courseSpringDataRepo;
	
	@Test
	public void testFindById() {
		Optional<Course> courseOptional = courseSpringDataRepo.findById(1L);
		System.out.println("testFindById() >> " + courseOptional.get());
		assertTrue(courseOptional.isPresent());
	}
	
	@Test
	public void testCourseNotPresent() {
		Optional<Course> courseOptional = courseSpringDataRepo.findById(99L);
		System.out.println("testCourseNotPresent() >> " + courseOptional.isPresent());
		assertFalse(courseOptional.isPresent());
	}
	
	@Test
	public void testCourseSort() {
		Sort sort = Sort.by(Direction.DESC, "name");//.and(Sort); --Like this you can add more operations.
		List<Course> allCourseList = courseSpringDataRepo.findAll(sort);
		System.out.println("testCourseSort() >> ");
		allCourseList.forEach(course -> System.out.println(course));
		assertFalse(allCourseList.isEmpty());
	}
	
}
