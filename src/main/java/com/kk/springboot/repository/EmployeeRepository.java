package com.kk.springboot.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Employee;
import com.kk.springboot.entity.FullTimeEmployee;
import com.kk.springboot.entity.PartTimeEmployee;

@Repository
@Transactional
public class EmployeeRepository {
	
	@Autowired
	EntityManager entityManager;
	
	public Employee findById(long id) {
		/**
		 * We can pass parent class i.e. Employee to entity manager in case of @Inheritance of any type is implemented
		 * over Employee class.
		 * Why >> Because @Inheritance keep maintain the inheritance hierarchy between them.
		 */
		Employee emp = entityManager.find(Employee.class, id);
		return emp;
	}
	
	public Employee findFullTimeEmployeeById(long id) {
		/**
		 * Pass child concrete class i.e. FullTimeEmployee instead of parent i.e. Employee in case of @MappedSuperclass
		 * implemented over Employee class.
		 * Why >> Because @MappedSuperclass eliminates inheritance hierarchy between them.
		 */
		Employee emp = entityManager.find(FullTimeEmployee.class, id);
		return emp;
	}
	
	public Employee findPartTimeEmployeeById(long id) {
		/**
		 * Pass child concrete class i.e. PartTimeEmployee instead of parent i.e. Employee in case of @MappedSuperclass
		 * implemented over Employee class.
		 * Why >> Because in @MappedSuperclass eliminates inheritance hierarchy between them.
		 */
		Employee emp = entityManager.find(PartTimeEmployee.class, id);
		return emp;
	}
	
	public Employee save(Employee emp) {
		entityManager.persist(emp);
		return emp;
	}
	
}
