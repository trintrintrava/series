package com.example.series.series.logic;

import com.example.series.series.domain.Actor;
import com.example.series.series.domain.Film;
import com.example.series.series.logic.service.ActorService;
import com.example.series.series.logic.service.NewActorService;
import com.example.series.series.logic.service.NewFilmService;
import com.example.series.series.repo.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/end-point/")
class TestController {

    final Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private NewActorService aservice;
    @Autowired
    private NewFilmService fservice;
    @Autowired
    private ActorRepository actorRepository;

    @GetMapping("t-point")
    public String getAllActors() {

        List<Actor> allActors = aservice.getAllActors();
        String result3 = Arrays.toString(allActors.toArray());

        List<Film> allFilms = fservice.getAllFilms();
        result3 += Arrays.toString(allFilms.toArray());

        return result3;

    }

    @GetMapping("get-actor-by-name")
    public String getActorsByName(@RequestParam("name") String actorName){
        List<Actor> actorsByName = aservice.getAllActorsByName(actorName);
        return Arrays.toString(actorsByName.toArray());
    }

    @GetMapping("get-actor-by-name-and-secondName")
    public String getActorsByName(@RequestParam("first") String actorName,
                                  @RequestParam("second") String actorSecondName){
        List<Actor> actorsByName = aservice.getAllActorsByNameAndSecondName(actorName, actorSecondName);
        return Arrays.toString(actorsByName.toArray());
    }

    @GetMapping("get-film-by-name-and-director")
    public String getFilmsByName(@RequestParam("first") String filmName,
                                  @RequestParam("second") String director){
        Film filmByNameAndDirector = fservice.getFilm(director, filmName);
        return filmByNameAndDirector.toString();
    }

    @GetMapping("get-actor-by-name-and-secondName-Native")
    public String getActorsByNameNative(@RequestParam("first") String actorName,
                                  @RequestParam("second") String actorSecondName){
        List<Actor> actorsByNameAndSecondName = aservice.getAllActorsByNameAndSecondNameNative(actorName, actorSecondName);
        return Arrays.toString(actorsByNameAndSecondName.toArray());
    }

    @PostMapping("create-new")
    public ResponseEntity<Actor> addActor(@RequestBody Actor actor){
        try {
            Actor aactor = aservice.saveNewActor(actor);
            return new ResponseEntity<>(aactor, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteActor(@PathVariable("id") long id) {
        try {
            actorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("test-point")
    public String getValue() {

        ActorService actorService = new ActorService();
        List<Actor> allActors = actorService.getAllActors();

        String result = getResultString(allActors);

        String result2 = Arrays.toString(allActors.toArray());

        return result2;

//        LOG.info("Got GET request");
//        return "TEST HELLO";
    }

    private String getResultString(List<Actor> allActors) {

        final StringBuilder sb = new StringBuilder("ActorList[");

        for (Actor item : allActors){
            sb.append(item.toString());
            sb.append(", ");
        }

        sb.append("]");

        return sb.toString();
    }
}