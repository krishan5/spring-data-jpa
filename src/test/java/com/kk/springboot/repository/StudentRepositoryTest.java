package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
	
}
