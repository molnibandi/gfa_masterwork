package com.greenfox.molnibandi_masterwork.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.greenfox.molnibandi_masterwork.models.dtos.ActorDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ActorRestControllerApiTest {

    private final MediaType contentTypeJson = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype());
    private final ObjectMapper mapper = new ObjectMapper();
    private final List<ActorDTO> dummyData = Arrays.asList(
            new ActorDTO(1L, "Bruce Willis", "Actor and musician Bruce Willis is well known for playing wisecracking or hard-edged characters, often in spectacular action films."),
            new ActorDTO(2L, "Carrie Fisher", "Carrie Frances Fisher was born on October 21, 1956 in Burbank, California, to singers/actors Eddie Fisher and Debbie Reynolds. She was an actress and writer."),
            new ActorDTO(3L, "Harrison Ford", "Harrison Ford was born on July 13, 1942 in Chicago, Illinois, to Dorothy (Nidelman), a radio actress, and Christopher Ford (born John William Ford), an actor turned advertising executive."),
            new ActorDTO(4L, "Mark Hamill", "Mark Hamill is best known for his portrayal of Luke Skywalker in the original Star Wars trilogy."),
            new ActorDTO(5L, "Sylvester Stallone", "Sylvester Stallone is a athletically built, dark-haired American actor/screenwriter/director/producer, the movie fans worldwide have been flocking to see Stallones films for over 40 years."),
            new ActorDTO(6L, "Ornella Muti", "Ornella Muti was born on March 9, 1955 in Rome, Lazio, Italy as Francesca Romana Rivelli.")
    );

    @Autowired
    private MockMvc mockMvc;

    public ActorRestControllerApiTest() {
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
    }

    @Test
    @DirtiesContext
    public void post_Actor_WorksCorrectly() throws Exception {
        ActorDTO newActor = new ActorDTO(null, "Test", "Test");

        String content = mapper.writeValueAsString(newActor);

        long expectedId = (long) dummyData.size() + 1;
        newActor.setId(expectedId);

        String expected = mapper.writeValueAsString(newActor);

        String actual = mockMvc.perform(
                        post("/api/moviedb-app/actors/")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/moviedb-app/actors/" + expectedId))
                .andExpect(content().contentType(contentTypeJson))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(mapper.readTree(expected), mapper.readTree(actual));

        actual = mockMvc.perform(get("/api/moviedb-app/actors/" + expectedId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(mapper.readTree(expected), mapper.readTree(actual));
    }

    @Test
    public void post_Actor_NullBody_ReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/moviedb-app/actors"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("400 BAD_REQUEST \"Required fields in the body: (Str) name, (Str) about\""));
    }

    @Test
    public void post_Actor_ObjectWithId_ReturnsBadRequest() throws Exception {
        String content = mapper.writeValueAsString(dummyData.get(0));
        mockMvc.perform(
                        post("/api/moviedb-app/actors")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("400 BAD_REQUEST \"Actor ID must be null!\""));
    }

    @Test
    public void post_Actor_ObjectWithExistedName_ReturnsBadRequest() throws Exception {
        ActorDTO newActor = new ActorDTO(null, "Carrie Fisher", "Test");

        String content = mapper.writeValueAsString(newActor);

        ResultActions perform = mockMvc.perform(
                post("/api/moviedb-app/actors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content));
        perform
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("400 BAD_REQUEST \"Name must be unique.\""));
    }

    @Test
    public void get_ActorId_ReturnsCorrectData() throws Exception {
        String expected = mapper.writeValueAsString(dummyData.get(0));

        String actual = mockMvc.perform(get("/api/moviedb-app/actors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(mapper.readTree(expected), mapper.readTree(actual));
    }

    @Test
    public void get_ActorId__WithNonexistentId_ReturnsNotFound() throws Exception {
        long id = 100L;
        mockMvc.perform(get("/api/moviedb-app/actors/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("404 NOT_FOUND \"Actor with ID: " + id + " cannot be found.\""));
    }

    @Test
    @DirtiesContext
    public void delete_ActorId_WorksCorrectly() throws Exception {
        String expected = mapper.writeValueAsString(dummyData.get(0));

        String actual = mockMvc.perform(delete("/api/moviedb-app/actors/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(mapper.readTree(expected), mapper.readTree(actual));

        mockMvc.perform(get("/api/moviedb-app/actors/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void delete_ActorId_WithNonexistentId_ReturnsNotFound() throws Exception {
        long id = 100L;
        mockMvc.perform(get("/api/moviedb-app/actors/" + id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("404 NOT_FOUND \"Actor with ID: " + id + " cannot be found.\""));
    }

    @Test
    @DirtiesContext
    public void put_Actor_ReturnsCorrectData() throws Exception {
        ActorDTO newActor = new ActorDTO(
                7L,
                "Dummy Name",
                "Dummy About"
        );

        String content = mapper.writeValueAsString(newActor);

        long id = 6L;
        newActor.setId((id));
        String expected = mapper.writeValueAsString(newActor);

        mockMvc.perform(
                        put("/api/moviedb-app/actors/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isNoContent());

        String actualQueried = mockMvc.perform(get("/api/moviedb-app/actors/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentTypeJson))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        assertEquals(mapper.readTree(expected), mapper.readTree(actualQueried));
    }

    @Test
    public void put_Actor_NullBody_ReturnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/moviedb-app/actors/2"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("400 BAD_REQUEST \"Required fields in the body: (Str) name, (Str) about\""));
    }

    @Test
    public void put_Actor_NullBodyAndNonexistentId_ReturnsBadRequest() throws Exception {
        mockMvc.perform(put("/api/moviedb-app/actors/100"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message")
                        .value("400 BAD_REQUEST \"Required fields in the body: (Str) name, (Str) about\""));
    }

    @Test
    public void put_Actor_NonexistentId_ReturnsBadRequestOnObject() throws Exception {
        String content = mapper.writeValueAsString(dummyData.get(0));
        long id = 100L;
        mockMvc.perform(
                        put("/api/moviedb-app/actors/" + id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message")
                        .value("404 NOT_FOUND \"Actor with ID: " + id + " cannot be found.\""));
    }

}
