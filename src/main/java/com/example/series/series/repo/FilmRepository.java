package com.example.series.series.repo;

import com.example.series.series.domain.Actor;
import com.example.series.series.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

//    объявление метода, для интерфейса все автоматом public
    @Query(value = "SELECT u FROM Film u WHERE u.director = :dir and u.name = :name", nativeQuery = false)
    Film findFilmByDirectorAndName(@Param("dir") String director,
                                         @Param("name") String name);

    @Query(value = "SELECT * FROM film f WHERE f.id not in (select r.film_id from review r where r.user_id = :id) ORDER BY f.rating DESC", nativeQuery = true)
    List<Film> findFilmsUserDidntSee(@Param("id") Long id);

    @Query(value = "SELECT * FROM film f WHERE f.id not in (select r.film_id from review r where r.user_id = :id) AND f.rating >5", nativeQuery = true)
    List<Film> findFilmsByRating(@Param("id") Long id);

    @Query(value = "SELECT * FROM film f WHERE f.director in (select f.director from review as r\n" +
            "join film as f on f.id = r.film_id where r.rating > 5 and r.user_id = :id) " +
            "and f.id not in (select r.film_id from review r where r.user_id = :id) ORDER BY f.rating DESC", nativeQuery = true)
    List<Film> findFilmsByDirector(@Param("id") Long id);



}
