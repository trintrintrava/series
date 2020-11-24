package com.example.series.series.logic;

import com.example.series.series.domain.Film;
import com.example.series.series.logic.service.RecomendationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController //аннотация на класс, отвечающий за точку входа
@RequestMapping("/advice-point/")
public class AdvicerController {

    final Logger LOG = LoggerFactory.getLogger(AdvicerController.class);

    @Autowired
    private RecomendationService recomendationService;

    @GetMapping("get-films-by-recomendation")
    public String getActorsByName(@RequestParam("user") String userName){
        List<Film> filmsByRecomendations = recomendationService.getRecomendations(userName);
        return Arrays.toString(filmsByRecomendations.toArray());
    }

}
