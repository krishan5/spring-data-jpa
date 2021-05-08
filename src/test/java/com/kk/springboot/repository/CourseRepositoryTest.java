package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kk.springboot.entity.Course;

@SpringBootTest
public class CourseRepositoryTest {
	
	Logger logger = LoggerFactory.getLogger(CourseRepositoryTest.class);
	
	@Autowired
	CourseRepository courseRepo;
	
	@Test
	void testFindById() {
		Course course = courseRepo.findById(1);
		assertEquals("Spring boot course", course.getName());
	}
	
}
