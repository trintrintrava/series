package com.example.series.series.repo;

import com.example.series.series.domain.ActToFilm;
import com.example.series.series.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActToFilmRepository extends JpaRepository<ActToFilm, Long> {

    @Query(value = "select DISTINCT atf.actor_id from el_j.act_to_film as atf where atf.film_id in :listId", nativeQuery = true)
    List<Long> findActorsByFilmsId(@Param("listId") List<Long> idFilms);

}
