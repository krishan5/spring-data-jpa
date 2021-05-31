package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
	
	@Test
	public void testCoursePagination() {
		PageRequest initialPage = PageRequest.of(0, 5);
		Page<Course> firstPage = courseSpringDataRepo.findAll(initialPage);
		System.out.println("testCoursePagination() >> First page >> ");
		List<Course> firstPageCourseList = firstPage.getContent();
		firstPageCourseList.forEach(course -> System.out.println(course));
		assertFalse(firstPageCourseList.isEmpty());
		
		Pageable nextPageable = initialPage.next();
		System.out.println("testCoursePagination() >> Next page >> ");
		Page<Course> nextPage = courseSpringDataRepo.findAll(nextPageable);
		List<Course> nextPageCourseList = nextPage.getContent();
		nextPageCourseList.forEach(course -> System.out.println(course));
		assertFalse(nextPageCourseList.isEmpty());
	}
	
	@Test
	@Transactional
	public void testCache() {
		System.out.println("testCache() >> Finding course 1st time.");
		/**
		 * If @Transactional is applied on testCache() and L2C is not configured,
		 * then this time data will be fetched from DB and Course + classes which it has (like Review) all will be cached 
		 * for this complete transaction and next time in same transaction it will be fetched from cache.
		 * 
		 * If @Transactional is not applied on testCache() and L2C is configured and only Course is made @Cacheable,
		 * then from wherever course is fetched 1st time it will be cached on the spot and where we use it again in any
		 * transaction you will get it from cache. But Review is not made @Cacheable so it will hit DB in every call.
		 * 
		 * If @Transactional is applied on testCache() and L2C is also configured and only Course is made @Cacheable
		 * but Review is not made @Cacheable, then course will behave same as explained above but review object will be
		 * fetched from DB in 1st time here and will be cached for this transaction, so in 2nd time review will be fetched
		 * from cache.
		 */
		courseSpringDataRepo.findById(1L);
		System.out.println("testCache() >> Finding course 2nd time.");
		courseSpringDataRepo.findById(1L);
	}
	
}
