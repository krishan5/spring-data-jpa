package com.kk.springboot;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.kk.springboot.entity.Course;
import com.kk.springboot.entity.Employee;
import com.kk.springboot.entity.FullTimeEmployee;
import com.kk.springboot.entity.PartTimeEmployee;
import com.kk.springboot.repository.CourseRepository;
import com.kk.springboot.repository.CourseRepositoryUsingJPQL;
import com.kk.springboot.repository.EmployeeRepository;

@SpringBootApplication
public class SpringbootApplication implements CommandLineRunner {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private CourseRepositoryUsingJPQL courseRepoJpql;
	
	@Autowired
	private EmployeeRepository empRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//EntityManager
		
		Course course = courseRepo.findById(1);
		logger.info("Course id=1 >> " + course);
		
		Course savedCourse = courseRepo.save(new Course("Java 16 course"));
		logger.info("Saved course >> " + savedCourse);
		
		savedCourse.setName("Java 16 course (updated)");
		Course updatedCourse = courseRepo.save(savedCourse);
		logger.info("Updated course >> " + updatedCourse);
		
		courseRepo.howTransactionalMakeImpact();
		courseRepo.howEntityManagerDetachWork();
		courseRepo.howEntityManagerClearWork();
		courseRepo.howEntityManagerFlushAndRefreshWork();
		
		//JPQL
		
		courseRepoJpql.select();
		courseRepoJpql.selectByType();
		courseRepoJpql.selectByTypeAndWhere();
		courseRepoJpql.selectNamedQuery();
		courseRepoJpql.selectWhereNamedQueries();
		
		//Native Queries
		
		courseRepoJpql.selectNativeQuery();
		/**
		 * TODO : To be done as it is throwing following exception :
		 * Caused by: java.lang.IllegalArgumentException: java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
		 */
		//courseRepoJpql.selectNamedNativeQuery();
		//courseRepoJpql.selectWhereNamedNativeQueries();
		
		courseRepoJpql.selectCoursesWithoutStudent();
		
		//Inheritance
		
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
		System.out.println(employee1);
		System.out.println(employee2);
	}

}
