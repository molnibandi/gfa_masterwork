package com.greenfox.molnibandi_masterwork.models.dtos;

import com.greenfox.molnibandi_masterwork.models.entities.Actor;
import com.greenfox.molnibandi_masterwork.models.entities.Director;
import com.greenfox.molnibandi_masterwork.models.entities.FilmStudio;
import com.greenfox.molnibandi_masterwork.models.entities.Movie;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Details about the movie")
public class MovieDTO {

    @Positive(message = "ID must be positive (0: invalid).")
    @ApiModelProperty(notes = "The unique ID of the movie")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Title must have a value.")
    @ApiModelProperty(notes = "The title of the movie")
    private String title;

    @ApiModelProperty(notes = "Cover image url")
    private String imageUrl;

    @Digits(integer = 4, fraction = 0, message = "Year is invalid.")
    @Min(value = 1888, message = "First Movie was ever filmed in 1888")
    @ApiModelProperty(notes = "The year when the movie was released")
    private int releaseYear;

    @Digits(integer = 2, fraction = 1, message = "Score is invalid.")
    @Min(value = 1, message = "Minimum rating is 1")
    @Max(value = 10, message = "Maximum rating is 10")
    @ApiModelProperty(notes = "The audience rating of the movie")
    private BigDecimal rating;

    @ApiModelProperty(notes = "Plot of the movie")
    private String description;

    @Digits(integer = 5, fraction = 1, message = "Amount is invalid.")
    @ApiModelProperty(notes = "The gross amount the movie made in million dollars")
    private BigDecimal gross;

    @NotEmpty(message = "Genre must have a value.")
    @ApiModelProperty(notes = "Genre of the movie")
    private String genre;

    @NotEmpty(message = "Name of the director must have an existing value.")
    @ApiModelProperty(notes = "Film studio that made the movie")
    private String filmStudioName;

    @NotEmpty(message = "Name of the film studio must have an existing value.")
    @ApiModelProperty(notes = "Director of the movie")
    private String directorName;

    @ApiModelProperty(notes = "Actors in the movie")
    private Set<String> actorsName = new HashSet<>();

    public MovieDTO() {
    }

    public MovieDTO(Long id, String title, String imageUrl, int releaseYear, BigDecimal rating, String description, BigDecimal gross, String genre, String filmStudioName, String directorName, Set<String> actorsName) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.description = description;
        this.gross = gross;
        this.genre = genre;
        this.filmStudioName = filmStudioName;
        this.directorName = directorName;
        this.actorsName = actorsName;
    }

    public static MovieDTO convertToMovieDTO(Movie movie) {
        return new MovieDTO(
                movie.getId(),
                movie.getTitle(),
                movie.getImageUrl(),
                movie.getReleaseYear(),
                movie.getRating(),
                movie.getDescription(),
                movie.getGross(),
                movie.getGenre(),
                movie.getFilmStudio().getName(),
                movie.getDirector().getName(),
                movie.getActors().stream().map(Actor::getName).collect(Collectors.toSet())
        );
    }

    public Movie convertToMovie(FilmStudio filmStudio, Director director, Set<Actor> actors) {
        return new Movie(
                this.id,
                this.title,
                this.imageUrl,
                this.releaseYear,
                this.rating,
                this.description,
                this.gross,
                this.genre,
                filmStudio,
                director,
                actors
        );
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

    public String getFilmStudioName() {
        return filmStudioName;
    }

    public void setFilmStudioName(String filmStudioName) {
        this.filmStudioName = filmStudioName;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public Set<String> getActorsName() {
        return actorsName;
    }

    public void setActorsName(Set<String> actorsName) {
        this.actorsName = actorsName;
    }

    @Override
    public String toString() {
        return "MovieDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", releaseYear=" + releaseYear +
                ", rating=" + rating +
                ", description='" + description + '\'' +
                ", gross=" + gross +
                ", genre='" + genre + '\'' +
                ", filmStudioName='" + filmStudioName + '\'' +
                ", directorName='" + directorName + '\'' +
                ", actorsName=" + actorsName +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovieDTO movieDTO = (MovieDTO) o;
        return Objects.equals(getFilmStudioName(), movieDTO.getFilmStudioName()) && Objects.equals(getDirectorName(), movieDTO.getDirectorName()) && Objects.equals(getActorsName(), movieDTO.getActorsName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFilmStudioName(), getDirectorName(), getActorsName());
    }

}
