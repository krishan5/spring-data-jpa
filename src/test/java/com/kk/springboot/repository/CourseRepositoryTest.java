package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kk.springboot.entity.Course;
import com.kk.springboot.entity.Review;
import com.kk.springboot.entity.ReviewRating;

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
	void testSave() {
		Course course = courseRepo.save(new Course("New course for testSave()"));
		course = courseRepo.findById(course.getId());
		assertEquals("New course for testSave()", course.getName());
	}
	
	@Test
	@DirtiesContext
	void testUpdate() {
		Course course = courseRepo.findById(3);
		assertEquals("Spring Webflux course", course.getName());
		
		course.setName("Spring Webflux course (updated in test)");
		courseRepo.save(course);
		
		course = courseRepo.findById(3);
		assertEquals("Spring Webflux course (updated in test)", course.getName());
	}
	
	//@Test
	@DirtiesContext
	/**
	 * Problem : Following test will actually delete the value from DB which can be used by another test or application. 
	 * @DirtiesContext tells spring to recover the updated/deleted value after executing the test.
	 */
	void testDeleteById() {
		courseRepo.deleteById(3);
		assertNull(courseRepo.findById(3));
	}
	
	@Test
	@DirtiesContext
	@Transactional
	void testAddReviewsInCourse() {
		//Creating reviews to add
		Review review1 = new Review("Great explanation and widely covered topics.", ReviewRating.VERY_GOOD.getValue());
		Review review2 = new Review("I'm beginner. It's too much information for me.", ReviewRating.AVERAGE.getValue());
		
		List<Review> newReviewList = new ArrayList<>();
		newReviewList.add(review1);
		newReviewList.add(review2);
		
		courseRepo.addReviewsInCourse(2, newReviewList);
		
		Course course = courseRepo.findById(2);
		List<Review> reviewList = course.getReviews();
		reviewList.forEach(r -> System.out.println(r));
		assertEquals(2, reviewList.size());
	}
	
}
