package com.kk.springboot.repository;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kk.springboot.entity.Course;

@Repository
@Transactional //Use it when perform delete/update/save operations so that we can have rollback support in case of any exception.
/**
 * @Transactional can either be set on class or on method. 
 * If it is set at class level then it means its all methods are @Transactional.
 * If it is set at method then that particular method supports @Transactional behavior.
 */
public class CourseRepository {
	
	@Autowired
	EntityManager entityManager;
	
	public Course findById(long id) {
		
		/**
		 * EntityManager find the Course on behalf of given primary key i.e. id
		 */
		return entityManager.find(Course.class, id);
	}
	
	public Course save(Course course) {
		if(course.getId() == 0) {
			entityManager.persist(course);
		}
		else {
			entityManager.merge(course);
		}
		return course;
	}
	
	public void deleteById(long id) {
		Course course = findById(id);
		entityManager.remove(course);
	}
	
	/**
	 * As we know @Transactional set at class level which make all methods @Transactional. So this method is @Transactional too as other methods.
	 * Now what is happening >> We are creating a new course then persist it using EntityManager.
	 * As soon as the course.setName() executed, as it changes the state of object and due to @Transactional these changes will be persisted.
	 * 
	 * Now point to understand here is that :
	 * whenever you are inside a transaction and you are managing something with the EntityManager (updating/deleting/inserting)
	 * that thing continuously managed by the EntityManager until the end of the transaction (i.e. untill the method is fully executed). 
	 * As we can see although we saved course first by EntityManager then whatever changes
	 * you are doing later in the transaction are also been tracked by the EntityManager and they are also persisted.
	 * That's why course.setName() changes tracked by EntityManager and persist those changes.
	 */
	public void howTransactionalMakeImpact() {
		Course course = new Course("Transactional effect");
		entityManager.persist(course);
		course.setName("Transactional effect on setName()");
	}
	
}
