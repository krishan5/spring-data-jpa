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
	
	public void howEntityManagerDetachWork() {
		Course angularCourse = new Course("Angular course");
		entityManager.persist(angularCourse);
		Course reactCourse = new Course("React course");
		entityManager.persist(reactCourse);
		
		/**
		 * The object you passed to detach() method will never be tracked by the EntityManager
		 * means if any changes made on that object after detach() will never be persist.
		 */
		entityManager.detach(angularCourse);
		
		angularCourse.setName("Angular course - updated (detach)"); //This changes will not reflect in DB as this object detached from EntityManager tracking.
		reactCourse.setName("React course - updated (detach)"); //This changes will be reflect in DB.
	}
	
	public void howEntityManagerClearWork() {
		Course mlCourse = new Course("Machine learning course");
		entityManager.persist(mlCourse);
		Course aiCourse = new Course("Artificial intelligence course");
		entityManager.persist(aiCourse);
		
		/**
		 * In case you want all objects to clear out from EntityManager tracking then this approach will be used.
		 * Detach is used to untrack the particular object.
		 * Clear is used to untrack all objects.
		 */
		entityManager.clear();
		
		//Following changes will not reflect in DB as all objects cleared out from EntityManager tracking.
		mlCourse.setName("Machine learning course - updated (clear)");
		aiCourse.setName("Artificial intelligence course - updated (clear)");
	}
	
	public void howEntityManagerFlushAndRefreshWork() {
		Course dockerCourse = new Course("Docker course");
		entityManager.persist(dockerCourse);
		Course kubernetesCourse = new Course("Kubernetes course");
		entityManager.persist(kubernetesCourse);
		
		dockerCourse.setName("Docker course - updated before flush()");
		
		/**
		 * Using flush() you can persist all the changes made earlier
		 * which can't be undo using refresh()
		 */
		entityManager.flush();
		
		dockerCourse.setName("Docker course - updated after flush()");
		kubernetesCourse.setName("Kubernetes course - updated");
		
		/**
		 * If any changes made done on object after executing any method of EntityManager,
		 * and we need its last committed changes back then refresh() helps here. 
		 * It refresh the state of the instance from the database,overwriting changes made to the entity, if any.
		 */
		entityManager.refresh(dockerCourse);
	}
	
}
