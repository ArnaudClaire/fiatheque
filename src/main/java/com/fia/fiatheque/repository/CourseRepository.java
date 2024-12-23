package com.fia.fiatheque.repository;

import com.fia.fiatheque.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}