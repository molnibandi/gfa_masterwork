package com.greenfox.molnibandi_masterwork.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "film_studios")
public class FilmStudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "ID must be positive (0: invalid).")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Name must have a value.")
    private String name;

    @Digits(integer = 4, fraction = 0, message = "Year is invalid.")
    @Min(value = 1892, message = "First Film studio was built in 1892")
    @Max(value = 2021, message = "Present or past years")
    private int founded;

    @NotEmpty(message = "address must have a value.")
    private String address;

    @JsonIgnore
    @OneToMany(mappedBy = "filmStudio")
    Set<Movie> movies = new HashSet<>();

    public FilmStudio() {
    }

    public FilmStudio(Long id, String name, int founded, String address) {
        this.id = id;
        this.name = name;
        this.founded = founded;
        this.address = address;
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

    public int getFounded() {
        return founded;
    }

    public void setFounded(int founded) {
        this.founded = founded;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Movie> getMovies() {
        return movies;
    }

    public void setMovies(Set<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "FilmStudio{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founded=" + founded +
                ", address='" + address + '\'' +
                ", movies=" + movies.stream().map(Movie::getTitle).collect(Collectors.toSet()) +
                '}';
    }

}