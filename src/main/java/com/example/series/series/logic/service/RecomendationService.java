package com.example.series.series.logic.service;

import com.example.series.series.domain.Film;
import com.example.series.series.domain.Users;
import com.example.series.series.repo.ActToFilmRepository;
import com.example.series.series.repo.ActorRepository;
import com.example.series.series.repo.FilmRepository;
import com.example.series.series.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecomendationService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ActToFilmRepository atfRepository;

    public List<Film> getRecomendations(String userName) {

//        1. выдать все фильмы которые user не смотрел
//        1.1 найти user_id
//        1.2 найти все фильмы, которые он смотрел
//        1.3 Вывести все остальные

        Users user = usersRepository.findUserByName(userName).get(0);

        List<Film> recomendations = filmRepository.findFilmsUserDidntSee(user.getId());

//        2. Выбрать фильмы, на которых у юзера больше 5 рейтинг и выдать фильмы тех же режиссеров

        List <Film> recomendations2 = filmRepository.findFilmsByDirector(user.getId());

//        3. Выбрать фильмы, на которых у юзера больше 5 рейтинг и выдать фильмы тех же актеров

        List <Film> recomendations3 = getRecomendationsByActors(user.getId());

//        4. Выбрать фильмы, на которых другие юзеры поставили рейтинг больше 5

        List<Film> recomendation4 = filmRepository.findFilmsByRatingOfOtherUsers(user.getId());

//        5. Выбрать фильмы, на которых основной рейтинг больше 5

        List <Film> recomendations5 = filmRepository.findFilmsByRating(user.getId());

//        6. Выбрать фильмы, с высокими оценками от userов того же гендера

        List<Film> recomendations6 = filmRepository.findFilmsByRatingOfOtherUsersSameGender(user.getId(), user.getGender());

//        7. Выбрать фильмы, с высокими оценками от userов той же возрастной категории

        LocalDate birth1 = user.getBirth().minusYears(5);
        LocalDate birth2 = user.getBirth().plusYears(5);
        List<Film> recomeendations7 = filmRepository.findFilmsByRatingOfOtherUsersSameAgeCategory(user.getId(), birth1, birth2);


        return recomendations6;
    }

    private List<Film> getRecomendationsByActors(Long id) {

        List<Long> idFilms = filmRepository.findFilmsByRatingOfUser(id);
        List<Long> idActors = atfRepository.findActorsByFilmsId(idFilms);
        List<Film> films = filmRepository.findNotSeenFilmsByActors(idActors, id);

        return films;

    }

}
