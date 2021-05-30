package com.kk.springboot.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.kk.springboot.entity.Course;
import com.kk.springboot.entity.Review;

@Repository
@Transactional //Use it when perform delete/update/save operations so that we can have rollback support in case of any exception.
/**
 * @Transactional can either be set on class or on method. 
 * If it is set at class level then it means its all methods are @Transactional.
 * If it is set at method then that particular method supports @Transactional behavior.
 * 
 * @Transactional also supports 1st level cache by default and 1st level cache is active by default.
 * 1st level cache is per transaction / persistence context which means when you fetch same data again and again
 * in single transaction, it will be fetched from DB for 1st time and from cache rest of the time. 
 * To use it effectively, the bounding of Transaction should start from then Service layer/Business layer. 
 */
public class CourseRepository {
	
	@Autowired
	/**
	 * Interface used to interact with the persistence context. 
	 */
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
		 * It refresh the state of the instance from the database/persistence context,
		 * overwriting changes made to the entity, if any.
		 * 
		 * It runs "select" query to fetch latest changes from DB/Persistence context.
		 */
		entityManager.refresh(dockerCourse); //You will see changes in DB >> Docker course - updated before flush()
	}
	
	public void addReviewsInCourse(long courseId, List<Review> newReviewList) {
		Course course = findById(courseId);
		//List<Review> reviewList = course.getReviews();
		
		for(Review review : newReviewList) {
			//Setting the relationship
			course.addReview(review);
			review.setCourse(course);
			
			/**
			 * Nothing following is required in case of EAGER initialization in 
			 * Course.java > @OneToMany relation on List<Review> 
			 */
			//entityManager.merge(course);
			//entityManager.persist(review);
		}
		
//		newReviewList
//			.forEach(review -> {
//				//Setting the relationship
//				course.addReview(review);
//				review.setCourse(course);
//				
//				//Save them to DB
//				/**
//		         * In case both classes has LAZY initialization : 
//				 * It works fine in case of merge(course) because when we call course.getReviews() it returns 2 reviews.
//				 * But, it not works fine in case of persist(review) because when we call course.getReviews() it returns 4 reviews,
//				 * each review with its duplicate entry.
//				 * TODO: I don't know why this is happening.
//				 */
//				entityManager.merge(course);
//			});
	}
	
}
