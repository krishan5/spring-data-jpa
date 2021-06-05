package com.kk.springboot.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String description;
	
	/**
	 * By default Enumerated is ORDINAL which simply depends upon the values are set in order like :
	 * ONE, TWO, THREE, FOUR and their values will be like : ONE = 1, TWO =2 , THREE =3 , FOUR = 4 
	 * Now in future we added ZERO to the start, then it will be like : ZERO, ONE, TWO, THREE, FOUR
	 * but there values will be like : ZERO = 1 , ONE = 2, TWO = 3 , THREE = 4, FOUR = 5
	 * So you can see the problem with ORDINAL. 
	 * Always use another type.
	 * 
	 * Note : If we go with EnumType.STRING then enum name will be saved in tables instead of its value.
	 * Eg : BAD(1) >> Here BAD is enum name and 1 is its value. But while saving in DB, it will be saved as 'BAD' as string, not 1 as int.
	 */
	@Enumerated(value = EnumType.STRING)
	private ReviewRating rating;
	
	//By default fetch strategy is EAGER for @OneToMany relation
	@ManyToOne(fetch = FetchType.LAZY)
	private Course course;

	public Review() {
	}
	
	public Review(String description, ReviewRating rating) {
		this.description = description;
		this.rating = rating;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReviewRating getRating() {
		return rating;
	}

	public void setRating(ReviewRating rating) {
		this.rating = rating;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public String toString() {
		return "Review [id=" + id + ", description=" + description + ", rating=" + rating + ", course=" + course.getId() + "]";
	}

}
