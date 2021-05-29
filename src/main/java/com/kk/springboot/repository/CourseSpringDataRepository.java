package com.kk.springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
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
