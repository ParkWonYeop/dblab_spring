package com.example.spring_dblab.repository;

import com.example.spring_dblab.entitiy.Event;
import com.example.spring_dblab.entitiy.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventReviewRepository extends JpaRepository<EventReview,Long> {
    List<EventReview> findByEvent(Event event);
}
