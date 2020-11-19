package com.example.series.series.repo;

import com.example.series.series.domain.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

//    объявление метода, для интерфейса все автоматом public
    @Query(value = "SELECT u FROM Film u WHERE u.director = :dir and u.name = :name", nativeQuery = false)
    Film findFilmByDirectorAndName(@Param("dir") String director,
                                         @Param("name") String name);


}
