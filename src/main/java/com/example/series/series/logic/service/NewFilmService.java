package com.example.series.series.logic.service;

import com.example.series.series.domain.Actor;
import com.example.series.series.domain.Film;
import com.example.series.series.repo.ActorRepository;
import com.example.series.series.repo.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewFilmService {

    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getAllFilms() {

        List<Film> filmList = filmRepository.findAll();
        return filmList;

    }

    public Film getFilm(String dir, String name) {

        Film film = filmRepository.findFilmByDirectorAndName(dir, name);
        return film;

    }

}
