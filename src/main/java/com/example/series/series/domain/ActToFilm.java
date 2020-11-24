package com.example.series.series.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ActToFilm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long actorId;
    private Long filmId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public Long getFilmId() {
        return filmId;
    }

    public void setFilmId(Long filmId) {
        this.filmId = filmId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ActToFilm{");
        sb.append("id=").append(id);
        sb.append(", actorId=").append(actorId);
        sb.append(", filmId=").append(filmId);
        sb.append('}');
        return sb.toString();
    }
}
