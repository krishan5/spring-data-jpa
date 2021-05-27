package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	
}
