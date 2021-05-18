package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kk.springboot.entity.Course;
import com.kk.springboot.entity.Passport;
import com.kk.springboot.entity.Student;

@SpringBootTest
public class StudentRepositoryTest {
	
	@Autowired
	StudentRepository studentRepo;
	
	@Test
	public void testFindById() {
		Student student = studentRepo.findById(1);
		assertEquals("Recker", student.getName());
		//assertEquals("Z235P3421", student.getPassport().getNumber());
	}
	
	@Test
	@DirtiesContext
	public void testSave() {
		Student student = new Student("Tony Stark");
		student = studentRepo.save(student);
		student = studentRepo.findById(student.getId());
		assertEquals("Tony Stark", student.getName());
	}
	
	@Test
	@DirtiesContext
	public void testUpdate() {
		Student student = studentRepo.findById(1);
		student.setName("Recker - updated");
		student = studentRepo.save(student);
		assertEquals("Recker - updated", student.getName());
	}
	
	@Test
	@DirtiesContext
	public void testDelete() {
		Student student = studentRepo.findById(4);
		assertNotNull(student);
		studentRepo.deleteById(4);
		student = studentRepo.findById(4);
		assertNull(student);
	}
	
	@Test
	@DirtiesContext
	public void testSaveStudentWithPassport() {
		Student student = new Student("Tony Stark");
		Passport passport = new Passport("P134N0337");
		student = studentRepo.saveStudentWithPassport(student, passport);
		assertEquals("Tony Stark", student.getName());
		assertEquals("P134N0337", student.getPassport().getNumber());
	}
	
	@Test
	@DirtiesContext
	@Transactional
	/**
	 * As we are using @OneToOne(fetch = FetchType.LAZY) on Passport variable in Student class,
	 * when we hit student.getPassport() then it needs transaction to be exist there
	 * otherwise it will throw :
	 * org.hibernate.LazyInitializationException: could not initialize proxy [com.kk.springboot.entity.Passport#1] - no Session
	 * 
	 * Note : Here session is Hibernate session.
	 */
	public void testFindStudentWithPassport() {
		Student student = studentRepo.findStudentWithPassport(1);
		assertEquals("Recker", student.getName());
		assertEquals("Z235P3421", student.getPassport().getNumber());
	}
	
	@Test
	@DirtiesContext
	@Transactional
	public void testFindStudentUsingPassportOnBehalfOfBidirectional() {
		Student student = studentRepo.findStudentUsingPassportOnBehalfOfBidirectional(1);
		assertEquals("Recker", student.getName());
		assertEquals("Z235P3421", student.getPassport().getNumber());
	}
	
	@Test
	@Transactional
	public void testFindStudentAndItsCourses() {
		Student student = studentRepo.findStudentAndItsCourses(1);
		assertEquals("Recker", student.getName());
		
		/**
		 * This will perform join STUDENT_COURSE table with COURSE table where student_id = 1
		 * due to LAZY fetching.
		 * This will also fetch data from REVIEW table due to EAGER fetching type on List<Review> in Course class.
		 */
		List<Course> courseList = student.getCourses();
		assertEquals(3, courseList.size());
	}
	
}