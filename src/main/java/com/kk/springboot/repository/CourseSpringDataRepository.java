package com.kk.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kk.springboot.entity.Course;

@Repository
public interface CourseSpringDataRepository extends JpaRepository<Course, Long>{
	
}
