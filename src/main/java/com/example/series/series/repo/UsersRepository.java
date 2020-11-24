package com.example.series.series.repo;

import com.example.series.series.domain.Actor;
import com.example.series.series.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
