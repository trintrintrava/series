package com.example.series.series.logic.service;

import com.example.series.series.domain.ActToFilm;
import com.example.series.series.domain.Film;
import com.example.series.series.domain.Review;
import com.example.series.series.domain.Users;
import com.example.series.series.repo.ActToFilmRepository;
import com.example.series.series.repo.FilmRepository;
import com.example.series.series.repo.ReviewRepository;
import com.example.series.series.repo.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendationNoSQLService {

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private ActToFilmRepository atfRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Film> getRecomendations(String userName) {

//        1. выдать все фильмы которые user не смотрел
//        1.1 найти user_id
//        1.2 найти все фильмы, которые он смотрел
//        1.3 Вывести все остальные

        Users user = usersRepository.findUserByName(userName).get(0);
        List<Film> allFilms = filmRepository.findAll();
        List<Review> allReview = reviewRepository.findAll();
        List<ActToFilm> allAtf = atfRepository.findAll();
        List<Users> allUsers = usersRepository.findAll();

        List<Film> unseenFilms = getUnseenFilms(user, allFilms, allReview);
        List<Film> unseenFilmsWithoutFor = userUnseenFilmsWithoutFor(user, allFilms, allReview);

        List<Film> recomendations = unseenFilms;
        // List<Film> recomendations = unseenFilmsWithoutFor; 2й вариант

//        2. Выбрать фильмы, на которых у юзера больше 5 рейтинг и выдать фильмы тех же режиссеров

        List <Film> recomendations2 = filmsWithSameDirectorOfFilmsWithRatingBiggerThan5OfUser(user, allFilms, allReview);

//        3. Выбрать фильмы, на которых у юзера больше 5 рейтинг и выдать фильмы тех же актеров

        List<Film> recomendations3 = getFilmsWithRatingByUserMoreThan5AndSameActor(user, allFilms, allReview, allAtf);

//        4. Выбрать фильмы, на которых другие юзеры поставили рейтинг больше 5

        List<Film> recomendation4 = getFilmsWithRatingBiggerThan5ByOtherUsers(user, allFilms, allReview);


//        5. Выбрать фильмы, на которых основной рейтинг больше 5

        List <Film> recomendations5 = getFilmsWithRatingBiggerThan5(user, allFilms, allReview);

//        6. Выбрать фильмы, с высокими оценками от userов того же гендера

        List<Film> recomendations6 = filmsWithRatingBiggerThan5FromUsersSameGenderUnseen(user, allFilms, allReview, allUsers);

//        7. Выбрать фильмы, с высокими оценками от userов той же возрастной категории

        LocalDate birth1 = user.getBirth().minusYears(5);
        LocalDate birth2 = user.getBirth().plusYears(5);

        List<Film> recomeendations7 = filmsWithRatingBiggerThan5FromUsersSameAge(user, allFilms, allReview, allUsers, birth1, birth2);

        return recomendations6;
    }

    private List<Film> filmsWithRatingBiggerThan5FromUsersSameAge(Users user, List<Film> allFilms, List<Review> allReview, List<Users> allUsers, LocalDate birth1, LocalDate birth2) {

        List<Long> usersIdSameAge = allUsers.stream()
                .filter(item -> (item.getBirth().compareTo(birth1) > 0 || item.getBirth().compareTo(birth2) < 0) && !item.getId().equals(user.getId()))
                .map(item -> item.getId())
                .collect(Collectors.toList());

        List<Long> filmsIdWithRatingBiggerThan5FromUsersSameAge = allReview.stream()
                .filter(item -> usersIdSameAge.contains(item.getUserId()) && item.getRating().compareTo(new BigDecimal(5)) > 0)
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Long> userSeenFilmsId = allReview.stream()
                .filter(item -> item.getUserId().equals(user.getId()))
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> recomendations = allFilms.stream()
                .filter(item -> !userSeenFilmsId.contains(item.getId()) && filmsIdWithRatingBiggerThan5FromUsersSameAge.contains(item.getId()))
                .collect(Collectors.toList());

        return recomendations;
    }

    private List<Film> filmsWithRatingBiggerThan5FromUsersSameGenderUnseen(Users user, List<Film> allFilms, List<Review> allReview, List<Users> users) {
        List<Long> usersWithSameGender = users.stream()
                .filter(item -> item.getGender().equals(user.getGender()))
                .map(item -> item.getId())
                .collect(Collectors.toList());

        List<Long> filmsIdWithRatingBiggerThan5FromUsersSameGenderUnseen = allReview.stream()
                .filter(item -> !item.getUserId().equals(user.getId()) && usersWithSameGender.contains(item.getId()) && item.getRating().compareTo(new BigDecimal(5)) > 0)
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> recomendations = allFilms.stream()
                .filter(item -> filmsIdWithRatingBiggerThan5FromUsersSameGenderUnseen.contains(item.getId()))
                .collect(Collectors.toList());

        return recomendations;
    }

    private List<Film> getFilmsWithRatingBiggerThan5(Users user, List<Film> allFilms, List<Review> allReview) {

        List<Long> userSeenFilmsId = allReview.stream()
                .filter(item -> item.getUserId().equals(user.getId()))
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> recomendations = allFilms.stream()
                .filter(item -> item.getRating() > 5 && !userSeenFilmsId.contains(item.getId()))
                .collect(Collectors.toList());

        return recomendations;
    }

    private List<Film> getFilmsWithRatingBiggerThan5ByOtherUsers(Users user, List<Film> allFilms, List<Review> allReview) {

        List<Long> filmsIdWithRatingBiggerThan5ByOtherUsers = allReview.stream()
                .filter(item -> !item.getUserId().equals(user.getId()) && item.getRating().compareTo(new BigDecimal(5)) > 0)
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> recomendations = allFilms.stream()
                .filter(item -> filmsIdWithRatingBiggerThan5ByOtherUsers.contains(item.getId()))
                .collect(Collectors.toList());

        return recomendations;
    }

    private List<Film> getFilmsWithRatingByUserMoreThan5AndSameActor(Users user, List<Film> allFilms, List<Review> allReview, List<ActToFilm> allAtf) {

        List<Long> filmsWithRatingBiggerThan5ByUser = allReview.stream()
                .filter(item -> {
                    BigDecimal rating = item.getRating();
                    Long reviewUserId = item.getUserId();
                    Long userId = user.getId();
                    return rating.compareTo(new BigDecimal(5)) > 0 && reviewUserId.equals(userId);
                })
                .map(item -> item.getFilmId()) // == (Review::getFilmId) - lambda
                .collect(Collectors.toList());

        List<Long> userSeenFilmsId = allReview.stream()
                .filter(item -> item.getUserId().equals(user.getId()))
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Long> actorsIdFromUserRatingMoreThan5AndNotSeen = allAtf.stream()
                .filter(item -> !userSeenFilmsId.contains(item.getFilmId()) && filmsWithRatingBiggerThan5ByUser.contains(item.getFilmId()))
                .map(item -> item.getActorId())
                .collect(Collectors.toList());

        List<Long> filmsIdWithActorsOfFilmsWithRatingMoreThan5AndUnseen = allAtf.stream()
                .filter(item -> actorsIdFromUserRatingMoreThan5AndNotSeen.contains(item.getActorId()))
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> recomendations = allFilms.stream()
                .filter(item -> filmsIdWithActorsOfFilmsWithRatingMoreThan5AndUnseen.contains(item.getId()))
                .collect(Collectors.toList());

        return recomendations;
    }

    private List<Film> filmsWithSameDirectorOfFilmsWithRatingBiggerThan5OfUser(Users user, List<Film> allFilms, List<Review> allReview) {
        List<Long> filmsWithRatingBiggerThan5ByUser = allReview.stream()
                .filter(item -> {
                    BigDecimal rating = item.getRating();
                    Long reviewUserId = item.getUserId();
                    Long userId = user.getId();
                    return rating.compareTo(new BigDecimal(5)) > 0 && reviewUserId.equals(userId);
                })
                .map(item -> item.getFilmId()) // == (Review::getFilmId) - lambda
                .collect(Collectors.toList());

        List<String> directorsList = allFilms.stream()
                .filter(item -> filmsWithRatingBiggerThan5ByUser.contains(item.getId()))
                .map(item -> item.getDirector())
                .collect(Collectors.toList());

        List<Long> userSeenFilmsId = allReview.stream()
                .filter(item -> item.getUserId().equals(user.getId()))
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> filmsOfSameDirectorsWithRatingMoreThan5ByUser = allFilms.stream()
                .filter(item -> directorsList.contains(item.getDirector()) && !userSeenFilmsId.contains(item.getId()))
                .collect(Collectors.toList());

        return filmsOfSameDirectorsWithRatingMoreThan5ByUser;
    }

    private List<Film> userUnseenFilmsWithoutFor(Users user, List<Film> allFilms, List<Review> allReview) {
        //Вариант 2 без for со stream

        List<Long> userSeenFilmsId = allReview.stream()
                .filter(item -> item.getUserId().equals(user.getId()))
                .map(item -> item.getFilmId())
                .collect(Collectors.toList());

        List<Film> userUnseenFilms = allFilms.stream()
                .filter(item -> !userSeenFilmsId.contains(item.getId()))
                .collect(Collectors.toList());

        return userUnseenFilms;
    }

    private List<Film> getUnseenFilms(Users user, List<Film> allFilms, List<Review> allReview) {

        //Вариант 1 2 for 2 списка

        List<Long> seenFilms = new ArrayList<Long>();

        for (Review review : allReview){
            if (user.getId().equals(review.getUserId())){
                seenFilms.add(review.getFilmId());
            }
        }

        List<Film> unseenFilms = new ArrayList<Film>();

        for (Film film : allFilms){
            if (!seenFilms.contains(film.getId())){
                unseenFilms.add(film);
            }
        }
        return unseenFilms;
    }

    private List<Film> getRecomendationsByActors(Long id) {

        List<Long> idFilms = filmRepository.findFilmsByRatingOfUser(id);
        List<Long> idActors = atfRepository.findActorsByFilmsId(idFilms);
        List<Film> films = filmRepository.findNotSeenFilmsByActors(idActors, id);

        return films;

    }

}
