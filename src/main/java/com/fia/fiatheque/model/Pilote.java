package com.fia.fiatheque.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import lombok.Data;
import java.util.List;

@Entity
public class Pilote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToOne(mappedBy = "pilote",cascade = CascadeType.ALL)
    @JsonManagedReference
    private Voiture voiture;

    @ManyToMany
    @JoinTable(
        name = "pilote_course",
        joinColumns = @JoinColumn(name = "pilote_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id"))
    private List<Course> courses;  // Liste des courses auxquelles le pilote participe

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Voiture getVoiture() {
        return voiture;
    }

    public void setVoiture(Voiture voiture) {
        this.voiture = voiture;
    }
}


