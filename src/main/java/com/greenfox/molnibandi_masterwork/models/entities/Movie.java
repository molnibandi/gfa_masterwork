package com.greenfox.molnibandi_masterwork.models.entities;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Positive(message = "ID must be positive (0: invalid).")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Title must have a value.")
    private String title;

    private String imageUrl;

    @Digits(integer = 4, fraction = 0, message = "Year is invalid.")
    @Min(value = 1888, message = "First Movie was ever filmed in 1888")
    @Max(value = 2021, message = "Present or past years")
    private int releaseYear;

    @Digits(integer = 2, fraction = 1, message = "Score is invalid.")
    @Range(min = 1, max = 10, message = "Minimum = 1, Maximum = 10")
    private BigDecimal rating;

    private String description;

    @Digits(integer = 5, fraction = 1, message = "Amount is invalid.")
    private BigDecimal gross;

    @NotEmpty(message = "Genre must have a value.")
    private String genre;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "film_studio_id", referencedColumnName = "id")
    @NotNull(message = "Film Studio must not be null.")
    private FilmStudio filmStudio;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    @NotNull(message = "Director must not be null.")
    private Director director;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = "casting",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actors = new HashSet<>();

    public Movie() {
    }

    public Movie(Long id, String title, String imageUrl, int releaseYear, BigDecimal rating, String description, BigDecimal gross, String genre, FilmStudio filmStudio, Director director, Set<Actor> actors) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.description = description;
        this.gross = gross;
        this.genre = genre;
        this.filmStudio = filmStudio;
        this.director = director;
        this.actors = actors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getGross() {
        return gross;
    }

    public void setGross(BigDecimal gross) {
        this.gross = gross;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public FilmStudio getFilmStudio() {
        return filmStudio;
    }

    public void setFilmStudio(FilmStudio filmStudio) {
        this.filmStudio = filmStudio;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public void setActors(Set<Actor> actors) {
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", gross=" + gross +
                ", genre='" + genre + '\'' +
                ", filmStudio=" + filmStudio +
                ", director=" + director +
                ", actors=" + actors.stream().map(Actor::getName).collect(Collectors.toSet()) +
                '}';
    }

}
