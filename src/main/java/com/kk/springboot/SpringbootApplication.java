package com.kk.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kk.springboot.entity.Course;
import com.kk.springboot.repository.CourseRepository;

@SpringBootApplication
public class SpringbootApplication implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository courseRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Course course = courseRepo.findById(1);
		logger.info("Course id=1 >> " + course);
		
		courseRepo.deleteById(1);
		course = courseRepo.findById(1);
		logger.info("Course id=1 >> after deletion >> " + course);
		
		Course savedCourse = courseRepo.save(new Course("Java 16 course"));
		logger.info("Saved course >> " + savedCourse);
		
		savedCourse.setName("Java 16 course (updated)");
		Course updatedCourse = courseRepo.save(savedCourse);
		logger.info("Updated course >> " + updatedCourse);
		
		courseRepo.howTransactionalMakeImpact();
	}

}
