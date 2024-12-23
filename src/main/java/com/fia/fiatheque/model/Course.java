package com.fia.fiatheque.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;
    private LocalDateTime date;

    @ManyToMany
    @JoinTable(
        name = "course_pilote",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "pilote_id"))
    private List<Pilote> pilotes;

}
