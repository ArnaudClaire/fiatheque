package com.fia.fiatheque.service;

import com.fia.fiatheque.model.Course;
import com.fia.fiatheque.model.Pilote;
import com.fia.fiatheque.repository.CourseRepository;
import com.fia.fiatheque.repository.PiloteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PiloteRepository piloteRepository;

    // Récupérer toutes les courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Récupérer une course par ID
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    // Créer une nouvelle course
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    // Mettre à jour une course
    public Course updateCourse(Long id, Course course) {
        if (courseRepository.existsById(id)) {
            course.setId(id);  // Assurez-vous de mettre à jour la course avec son ID
            return courseRepository.save(course);
        }
        return null;  // Retourner null si la course n'existe pas
    }

    // Supprimer une course
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    // Récupérer les pilotes par leurs IDs
    public List<Pilote> getPilotesByIds(List<Long> piloteIds) {
        return piloteRepository.findAllById(piloteIds);  // Utilise findAllById pour récupérer les pilotes par liste d'IDs
    }

    // Sauvegarder une course
    public void saveCourse(Course course) {
        courseRepository.save(course);  // Sauvegarder la course après l'avoir modifiée
    }
}
