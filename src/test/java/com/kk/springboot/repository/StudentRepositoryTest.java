package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

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
	
}
