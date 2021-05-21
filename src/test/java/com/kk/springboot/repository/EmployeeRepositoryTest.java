package com.kk.springboot.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import com.kk.springboot.entity.Employee;
import com.kk.springboot.entity.FullTimeEmployee;
import com.kk.springboot.entity.PartTimeEmployee;

@SpringBootTest
public class EmployeeRepositoryTest {
	
	@Autowired
	EmployeeRepository empRepo;
	
	@Test
	@DirtiesContext
	public void testSaveAndFindById() {
		Employee emp1 = new FullTimeEmployee("Emp1", new BigDecimal(1000000));
		Employee emp2 = new PartTimeEmployee("Emp2", new BigDecimal(500));
		empRepo.save(emp1);
		empRepo.save(emp2);
		
		/**
		 * Use this way of fetching when @Inheritance is implemented over Employee class.
		 */
		//Employee employee1 = empRepo.findById(1);
		//Employee employee2 = empRepo.findById(2);
		
		/**
		 * Use this way of fetching when @MappedSuperclass is implemented over Employee class.
		 */
		Employee employee1 = empRepo.findFullTimeEmployeeById(1);
		Employee employee2 = empRepo.findPartTimeEmployeeById(2);
		assertEquals("Emp1", employee1.getName());
		assertEquals("Emp2", employee2.getName());
	}
	
}
