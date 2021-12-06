package com.greenfox.molnibandi_masterwork.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "ID must be positive (0: invalid).")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Name must have a value.")
    private String name;

    @NotEmpty(message = "About must have a value.")
    private String about;

    @JsonIgnore
    @ManyToMany(mappedBy = "actors")
    Set<Movie> movies = new HashSet<>();

    public Actor() {
    }

    public Actor(Long id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", movies=" + movies.stream().map(Movie::getTitle).collect(Collectors.toSet()) +
                '}';
    }

}
