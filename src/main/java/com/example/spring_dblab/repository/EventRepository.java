package com.example.spring_dblab.repository;

import com.example.spring_dblab.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event,Long> {
    List<Event> findAll();
}
