package com.greenfox.molnibandi_masterwork.models.dtos;

import com.greenfox.molnibandi_masterwork.models.entities.FilmStudio;
import com.greenfox.molnibandi_masterwork.models.entities.Movie;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Details about the film studio")
public class FilmStudioDTO {

    @Positive(message = "ID must be positive (0: invalid).")
    @ApiModelProperty(notes = "The unique ID of the film studio")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Name must have a value.")
    @ApiModelProperty(notes = "Name of the film studio")
    private String name;

    @Digits(integer = 4, fraction = 0, message = "Year is invalid.")
    @Min(value = 1892, message = "First Film studio was built in 1892")
    @ApiModelProperty(notes = "The year when the film studio was founded")
    private int founded;

    @NotEmpty(message = "address must have a value.")
    @ApiModelProperty(notes = "The address of the film studio")
    private String address;

    @ApiModelProperty(notes = "Movie titles that the film studio made")
    private Set<String> movieTitles = new HashSet<>();


    public FilmStudioDTO() {
    }

    public FilmStudioDTO(Long id, String name, int founded, String address) {
        this.id = id;
        this.name = name;
        this.founded = founded;
        this.address = address;
    }

    public FilmStudioDTO(Long id, String name, int founded, String address, Set<String> movieTitles) {
        this.id = id;
        this.name = name;
        this.founded = founded;
        this.address = address;
        this.movieTitles = movieTitles;
    }

    public static FilmStudioDTO convertToDTOForSaving(FilmStudio filmStudio) {
        return new FilmStudioDTO(
                filmStudio.getId(),
                filmStudio.getName(),
                filmStudio.getFounded(),
                filmStudio.getAddress());
    }

    public static FilmStudioDTO convertToDTOForListing(FilmStudio filmStudio) {
        return new FilmStudioDTO(
                filmStudio.getId(),
                filmStudio.getName(),
                filmStudio.getFounded(),
                filmStudio.getAddress(),
                filmStudio.getMovies().stream().map(Movie::getTitle).collect(Collectors.toSet()));
    }

    public FilmStudio convertToFilmStudio() {
        return new FilmStudio(this.id, this.name, this.founded, this.address);
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

    public Set<String> getMovieTitles() {
        return movieTitles;
    }

    public void setMovieTitles(Set<String> movieTitles) {
        this.movieTitles = movieTitles;
    }

    @Override
    public String toString() {
        return "FilmStudioDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", founded=" + founded +
                ", address='" + address + '\'' +
                ", movieTitles=" + movieTitles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilmStudioDTO that = (FilmStudioDTO) o;
        return Objects.equals(getMovieTitles(), that.getMovieTitles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieTitles());
    }

}
