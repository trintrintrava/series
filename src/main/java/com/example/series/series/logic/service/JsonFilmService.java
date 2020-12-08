package com.example.series.series.logic.service;

import com.example.series.series.domain.Film;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonFilmService {

    public String getJsonFromFilm(Film film) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(film);
        return jsonString;
    }

    public String getJsonFromFilms(List<Film> films) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(films);
        return jsonString;
    }

    public Film getFilmFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Film film = objectMapper.readValue(jsonString, Film.class);
        return film;
    }

    public List<Film> getFilmsFromJson(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Film> films = objectMapper.readValue(jsonString, new TypeReference<List<Film>>(){});
        return films;
    }

}
