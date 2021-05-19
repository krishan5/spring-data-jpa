package com.kk.springboot.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Employee;

@Repository
@Transactional
public class EmployeeRepository {
	
	@Autowired
	EntityManager entityManager;
	
	public Employee findById(long id) {
		Employee emp = entityManager.find(Employee.class, id);
		return emp;
	}
	
	public Employee save(Employee emp) {
		entityManager.persist(emp);
		return emp;
	}
	
}
