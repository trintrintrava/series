package com.example.series.series.repo;

import com.example.series.series.domain.Actor;
import com.example.series.series.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

//    объявление метода, для интерфейса все автоматом public
    @Query(value = "SELECT u FROM Film u WHERE u.director = :dir and u.name = :name", nativeQuery = false)
    Film findFilmByDirectorAndName(@Param("dir") String director,
                                         @Param("name") String name);

    @Query(value = "SELECT * FROM film f WHERE f.id not in " +
            "(select r.film_id from review r where r.user_id = :id) ORDER BY f.rating DESC",
            nativeQuery = true)
    List<Film> findFilmsUserDidntSee(@Param("id") Long id);

    @Query(value = "SELECT * FROM film f " +
            "WHERE f.id not in (select r.film_id from review r where r.user_id = :id) " +
            "AND f.rating >5", nativeQuery = true)
    List<Film> findFilmsByRating(@Param("id") Long id);

    @Query(value = "SELECT * FROM film f WHERE f.director in (select f.director from review as r " +
            "join film as f on f.id = r.film_id where r.rating > 5 and r.user_id = :id) " +
            "and f.id not in (select r.film_id from review r where r.user_id = :id) ORDER BY f.rating DESC", nativeQuery = true)
    List<Film> findFilmsByDirector(@Param("id") Long id);

    @Query(value = "SELECT * FROM el_j.film f " +
            "WHERE f.id in (select r.film_id from el_j.review as r where r.rating>5 and r.user_id != :id) " +
            "ORDER BY f.rating DESC", nativeQuery = true)
    List<Film> findFilmsByRatingOfOtherUsers(@Param("id") Long id);

    @Query(value = "SELECT * FROM el_j.film f " +
            "WHERE f.id in (select r.film_id " +
            "           from el_j.review as r " +
            "           join el_j.users as u on u.id = r.user_id " +
            "           where r.rating>5 and r.user_id != :id and u.gender = :gender) " +
            "ORDER BY f.rating DESC", nativeQuery = true)
    List<Film> findFilmsByRatingOfOtherUsersSameGender(@Param("id") Long id,
                                                       @Param("gender") String userGender);

    @Query(value = "select DISTINCT r.film_id from el_j.review as r where r.rating>5 and r.user_id = :id", nativeQuery = true)
    List<Long> findFilmsByRatingOfUser(@Param("id") Long id);

    @Query(value = "select * from el_j.film as f join el_j.act_to_film as atf on atf.film_id = f.id " +
            "where atf.actor_id in :idActors and f.id not in (select r.film_id from review r where r.user_id = :id)", nativeQuery = true)
    List<Film> findNotSeenFilmsByActors(@Param("idActors") List<Long> idActors,
                                        @Param("id") Long id);

    @Query(value = "SELECT * FROM el_j.film f " +
            "WHERE f.id in (select r.film_id " +
            "           from el_j.review as r " +
            "           join el_j.users as u on u.id = r.user_id " +
            "           where r.rating > 5 and r.user_id != :id and (u.birth < :birth2 and u.birth > birth1)) " +
            "ORDER BY f.rating DESC", nativeQuery = true)
    List<Film> findFilmsByRatingOfOtherUsersSameAgeCategory(@Param("id") Long id, @Param("birth1") LocalDate birth1, @Param("birth2") LocalDate birth2);
}
