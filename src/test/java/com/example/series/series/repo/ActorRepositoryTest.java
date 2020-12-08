package com.example.series.series.repo;

import com.example.series.series.domain.Actor;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ActorRepositoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Autowired
    ActorRepository actorRepository;

    @Test
    public void testActorRepository() {
        Actor act = new Actor();
        act.setName("name");
        act.setSecondName("secName");


        actorRepository.save(act);


        Assert.assertNotNull(act.getId());
    }
}