package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kk.springboot.entity.Course;

@SpringBootTest
public class CourseRepositoryTest {
	
	private Logger logger = LoggerFactory.getLogger(CourseRepositoryTest.class);
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Test
	void testFindById() {
		Course course = courseRepo.findById(3);
		assertEquals("Spring Webflux course", course.getName());
	}
	
	@Test
	@DirtiesContext
	/**
	 * Problem : Following test will actually delete the value from DB which can be used by another test or application. 
	 * @DirtiesContext tells spring to recover the updated/deleted value after executing the test.
	 */
	void testDeleteById() {
		courseRepo.deleteById(2);
		assertNull(courseRepo.findById(2));
	}
	
}
