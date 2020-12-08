package com.example.series.series.logic.service;

import com.example.series.series.domain.Film;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JsonFilmServiceTest {
    //если запустить весь класс, тесты запускаются в случайном порядке !!

    @Before
    public void setUp() throws Exception {
        //выполняется перед каждым тестом
        System.out.println("this is setUp");
    }

    @After
    public void tearDown() throws Exception {
        //выполняется после каждого теста
        System.out.println("this is tearDown");
    }

    @Test
    public void getJsonFromFilm_test1() throws JsonProcessingException {
        System.out.println("this is test1");
        Film film = new Film();
        film.setName("filmName");
        film.setDirector("filmDirector");
        film.setRating(1);
        JsonFilmService jsonFilmService = new JsonFilmService(); //прекондишн - две пуст строки - тестовая строка - две пуст строки


        String jsonFilm = jsonFilmService.getJsonFromFilm(film);


        System.out.println(jsonFilm);
        String expectedString = "{\"id\":null,\"name\":\"filmName\",\"director\":\"filmDirector\",\"rating\":1}";
        Assert.assertEquals(expectedString, jsonFilm);
    }

    @Test
    public void getJsonFromFilms_test2() throws JsonProcessingException {
        System.out.println("this is test2");
        List<Film> films = new ArrayList<>();
        Film film1 = new Film();
        film1.setName("filmName");
        film1.setDirector("filmDirector");
        film1.setRating(1);
        Film film2 = new Film();
        film2.setName("filmName2");
        film2.setDirector("filmDirector2");
        film2.setRating(2);
        films.add(film1);
        films.add(film2);
        JsonFilmService jsonFilmService = new JsonFilmService();


        String jsonFilms = jsonFilmService.getJsonFromFilms(films);


        System.out.println(jsonFilms);
        String expectedString = "[{\"id\":null,\"name\":\"filmName\",\"director\":\"filmDirector\",\"rating\":1},{\"id\":null,\"name\":\"filmName2\",\"director\":\"filmDirector2\",\"rating\":2}]";
        Assert.assertEquals(expectedString, jsonFilms);
    }

    @Test
    public void getFilmFromJson_test3() throws JsonProcessingException {
        System.out.println("this is test3");
        String jsonString = "{\"id\":null,\"name\":\"filmName\",\"director\":\"filmDirector\",\"rating\":1}";
        JsonFilmService jsonFilmService = new JsonFilmService();


        Film film = jsonFilmService.getFilmFromJson(jsonString);


        System.out.println(film.toString());
        Film expectedFilm = new Film();
        expectedFilm.setName("filmName");
        expectedFilm.setDirector("filmDirector");
        expectedFilm.setRating(2);
        Assert.assertEquals(expectedFilm, film);
    }

    @Test(expected = JsonProcessingException.class) //проверка выскакивания exception
    public void getFilmFromJson_test5() throws JsonProcessingException {
        System.out.println("this is test5");
        String jsonString = "123";
        JsonFilmService jsonFilmService = new JsonFilmService();


        Film film = jsonFilmService.getFilmFromJson(jsonString);
    }

    @Test
    public void getFilmsFromJson_test4() throws JsonProcessingException {
        System.out.println("this is test4");
        String jsonString = "[{\"id\":null,\"name\":\"filmName\",\"director\":\"filmDirector\",\"rating\":1},{\"id\":null,\"name\":\"filmName2\",\"director\":\"filmDirector2\",\"rating\":2}]";
        JsonFilmService jsonFilmService = new JsonFilmService();


        List<Film> films = jsonFilmService.getFilmsFromJson(jsonString);


        List<Film> expectedFilms = new ArrayList<>();
        Film film1 = new Film();
        film1.setName("filmName");
        film1.setDirector("filmDirector");
        film1.setRating(1);
        Film film2 = new Film();
        film2.setName("filmName2");
        film2.setDirector("filmDirector2");
        film2.setRating(2);
        expectedFilms.add(film1);
        expectedFilms.add(film2);
        Assert.assertEquals(expectedFilms, films);
    }

    @Test(expected = JsonProcessingException.class)
    public void getFilmsFromJson_test6() throws JsonProcessingException {
        System.out.println("this is test6");
        String jsonString = "{\"id\":null,\"name\":\"filmName\",\"director\":\"filmDirector\",\"rating\":1}";
        JsonFilmService jsonFilmService = new JsonFilmService();


        List<Film> films = jsonFilmService.getFilmsFromJson(jsonString);
    }
}