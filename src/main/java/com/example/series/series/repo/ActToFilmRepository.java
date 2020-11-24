package com.example.series.series.repo;

import com.example.series.series.domain.ActToFilm;
import com.example.series.series.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActToFilmRepository extends JpaRepository<ActToFilm, Long> {
}
