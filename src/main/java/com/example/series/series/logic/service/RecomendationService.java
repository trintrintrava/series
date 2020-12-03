package com.example.series.series.logic.service;

import com.example.series.series.domain.Film;
import com.example.series.series.repo.FilmRepository;
import com.example.series.series.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecomendationService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FilmRepository filmRepository;

    public List<Film> getRecomendations(String userName) {

//        1. выдать все фильмы которые user не смотрел
//        1.1 найти user_id
//        1.2 найти все фильмы, которые он смотрел
//        1.3 Вывести все остальные

        Long id = usersRepository.findUsersIdByName(userName);

        List<Film> recomendations = filmRepository.findFilmsUserDidntSee(id);

//        2. Выбрать фильмы, на которых у юзера больше 5 рейтинг и выдать фильмы тех же режиссеров

        List <Film> recomendations2 = filmRepository.findFilmsByDirector(id);

//        -3. Выбрать фильмы, на которых у юзера больше 5 рейтинг и выдать фильмы тех же актеров
//        4. Выбрать фильмы, на которых другие юзеры поставили рейтинг больше 5

        List<Film> recomendation4 = filmRepository.findFilmsByRatingOfOtherUsers(id);

//        5. Выбрать фильмы, на которых основной рейтинг больше 5

        List <Film> recomendations5 = filmRepository.findFilmsByRating(id);

//        6. Выбрать фильмы, с высокими оценками от userов того же гендера

        String userGender = usersRepository.findUsersGenderByName(id);
        List<Film> recomendations6 = filmRepository.findFilmsByRatingOfOtherUsersSameGender(id, userGender);

//        -7. Выбрать фильмы, с высокими оценками от userов той же возрастной категории

        return recomendations6;
    }

//    select *
//    from film as f
//    where f.id not in (select distinct r.film_id from review as r where r.user_id = ?)
//    order by f.rating desc

//    select u.id
//    from users as u
//    where u.name = userName

}
