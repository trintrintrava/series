package com.example.series.series.logic;

import com.example.series.series.domain.Actor;
import com.example.series.series.logic.service.ActorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/end-point/")
class TestController {

    final Logger LOG = LoggerFactory.getLogger(TestController.class);

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