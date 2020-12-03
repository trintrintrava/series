package com.example.series.series.repo;

import com.example.series.series.domain.Actor;
import com.example.series.series.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT u.id FROM users u WHERE u.name = :name", nativeQuery = true)
    Long findUsersIdByName(@Param("name") String name);

    @Query(value = "SELECT u.gender FROM users u WHERE u.id = :id", nativeQuery = true)
    String findUsersGenderByName(@Param("id") Long id);
}
