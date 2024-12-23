package com.fia.fiatheque.controller;

import com.fia.fiatheque.model.Course;
import com.fia.fiatheque.model.Pilote;
import com.fia.fiatheque.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Récupérer toutes les courses
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    // Récupérer une course par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // Créer une nouvelle course
    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        Course createdCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // Ajouter des pilotes à une course
    @PostMapping("/{courseId}/pilotes")
    public ResponseEntity<Course> addPilotesToCourse(@PathVariable Long courseId, @RequestBody List<Long> piloteIds) {
        Optional<Course> courseOptional = courseService.getCourseById(courseId);  // Utilisation du service
        if (courseOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Course course = courseOptional.get();
        List<Pilote> pilotes = courseService.getPilotesByIds(piloteIds);  // Méthode dans le service pour récupérer les pilotes
        if (pilotes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();  // Si aucun pilote n'est trouvé
        }

        course.getPilotes().addAll(pilotes);
        courseService.saveCourse(course);  // Sauvegarder la course mise à jour via le service
        return ResponseEntity.ok(course);
    }

    // Mettre à jour une course
    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        return updatedCourse != null ? ResponseEntity.ok(updatedCourse)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    // Supprimer une course
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        if (course.isPresent()) {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Démarrer la course avec une vitesse donnée
    @PostMapping("/{courseId}/vitesse/{vitesse}/start")
    public ResponseEntity<String> startCourse(@PathVariable Long courseId, @PathVariable int vitesse) {
        Optional<Course> courseOptional = courseService.getCourseById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course non trouvée.");
        }

        Course course = courseOptional.get();
        List<String> participants = course.getPilotes().stream()
                .filter(pilote -> {
                    Voiture voiture = pilote.getVoiture();
                    // Vérification avec 'speedLimit' de la voiture
                    return voiture != null && voiture.getSpeedLimit() >= vitesse;
                })
                .map(pilote -> {
                    Voiture voiture = pilote.getVoiture();
                    return "Le pilote '" + pilote.getNom() + "' peut participer à la course avec sa voiture '" + voiture.getModele() + "'";
                })
                .collect(Collectors.toList());

        if (participants.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aucun pilote ne peut participer à la course avec cette vitesse.");
        }

        // Joindre les messages des pilotes
        String message = String.join("\n", participants);
        return ResponseEntity.ok(message);
    }
}
