package com.example.series.series.logic.service;

import com.example.series.series.domain.Actor;
import com.example.series.series.repo.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> getAllActors() {

        List<Actor> actorList = actorRepository.findAll();
        return actorList;

    }

}
