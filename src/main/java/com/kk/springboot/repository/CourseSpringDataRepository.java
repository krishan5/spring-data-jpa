package com.kk.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

/**
 * It is the quickest way to exposing repository end points using @RepositoryRestController.
 * It is not recommended to use it in production.
 * You can access it like : 
 * http://localhost:8080/courses [To get all courses]
 * http://localhost:8080/courses/1 [To get course has ID = 1]
 */
//@RepositoryRestController

/**
 * It is the quickest way to exposing repository end points using @RepositoryRestResource where you can mention the path manually.
 * It is not recommended to use it in production.
 * You can access it like : 
 * http://localhost:8080/courses [To get all courses]
 * http://localhost:8080/courses/1 [To get course has ID = 1]
 */
@RepositoryRestResource(path = "courses")
public interface CourseSpringDataRepository extends JpaRepository<Course, Long> {
	List<Course> findByName(String name);
	
	List<Course> deleteByName(String name);
	
	List<Course> findByNameOrderByIdDesc(String name);
	
	List<Course> countByName(String name);
	
	List<Course> findByNameAndId(String name, Long id);
	
	@Query("select c from Course c where c.id = :id")
	List<Course> customJPQL(Long id);
	
	@Query(value = "select * from Course where id = :id" ,nativeQuery = true)
	List<Course> customNativeQuery(Long id);
}
