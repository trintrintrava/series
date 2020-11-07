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

    public Actor saveNewActor(Actor actor) {

        Actor actorList = actorRepository.save(actor);
        return actorList;

    }

    public void deleteActor(long id) {

        actorRepository.deleteById(id);

    }

    public List<Actor> getAllActorsByName(String name) {

        List<Actor> actorList = actorRepository.findByName(name);
        return actorList;
    }

    public List<Actor> getAllActorsByNameAndSecondName(String name, String secName) {

        List<Actor> actorList = actorRepository.findByNameAndSecondName(name, secName);
        return actorList;
    }

    public List<Actor> getAllActorsByNameAndSecondNameNative(String name, String secName) {

        List<Actor> actorList = actorRepository.findActorByNameAndSecondName(name, secName);
        return actorList;
    }

}
