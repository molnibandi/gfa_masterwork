package com.greenfox.molnibandi_masterwork.models.dtos;

import com.greenfox.molnibandi_masterwork.models.entities.Actor;
import com.greenfox.molnibandi_masterwork.models.entities.Movie;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApiModel(description = "Details about the actor")
public class ActorDTO {

    @Positive(message = "ID must be positive (0: invalid).")
    @ApiModelProperty(notes = "The unique ID of the actor")
    private Long id;

    @Column(unique = true)
    @NotEmpty(message = "Name must have a value.")
    @ApiModelProperty(notes = "Name of the actor")
    private String name;

    @NotEmpty(message = "About must have a value.")
    @ApiModelProperty(notes = "Short bio of the actor")
    private String about;

    @ApiModelProperty(notes = "Movie titles the actor starred in")
    private Set<String> movieTitles = new HashSet<>();

    public ActorDTO() {
    }

    public ActorDTO(Long id, String name, String about) {
        this.id = id;
        this.name = name;
        this.about = about;
    }

    public ActorDTO(Long id, String name, String about, Set<String> movieTitles) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.movieTitles = movieTitles;
    }

    public static ActorDTO convertToDTOForSaving(Actor actor) {
        return new ActorDTO(
                actor.getId(),
                actor.getName(),
                actor.getAbout());
    }

    public static ActorDTO convertToDTOForListing(Actor actor) {
        return new ActorDTO(
                actor.getId(),
                actor.getName(),
                actor.getAbout(),
                actor.getMovies().stream().map(Movie::getTitle).collect(Collectors.toSet()));
    }

    public Actor convertToActor() {
        return new Actor(this.id, this.name, this.about);
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

    public Set<String> getMovieTitles() {
        return movieTitles;
    }

    public void setMovieTitles(Set<String> movieTitles) {
        this.movieTitles = movieTitles;
    }

    @Override
    public String toString() {
        return "ActorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", movieTitles=" + movieTitles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDTO actorDTO = (ActorDTO) o;
        return Objects.equals(getMovieTitles(), actorDTO.getMovieTitles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieTitles());
    }

}

