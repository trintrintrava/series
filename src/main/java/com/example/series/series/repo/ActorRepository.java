package com.example.series.series.repo;

import com.example.series.series.domain.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {

    public List<Actor> findByName(String name);
    public List<Actor> findByGender(String gender);
    public List<Actor> findByNameAndSecondName(String name, String secondName);

    @Query(value = "SELECT * FROM actor a WHERE a.name = :name and a.second_name = :secName", nativeQuery = true)
    List<Actor> findActorByNameAndSecondName(@Param("name") String name,
                                   @Param("secName") String secondName);


}
