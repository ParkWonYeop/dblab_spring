package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.EventDto;
import com.example.spring_dblab.entitiy.Event;
import com.example.spring_dblab.entitiy.EventReview;
import com.example.spring_dblab.entitiy.User;
import com.example.spring_dblab.repository.EventRepository;
import com.example.spring_dblab.repository.EventReviewRepository;
import com.example.spring_dblab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.spring_dblab.utils.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final EventReviewRepository eventReviewRepository;

    public List<Event> getEvent() {
        try {
            List<Event> eventList = eventRepository.findAll();
            return eventList;
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public List<Event> findEvent(String eventName) {
        try {
            List<Event> eventList = eventRepository.findByName(eventName);
            return eventList;
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }

    }

    public List<Event> findEventByDate(LocalDate startDate,LocalDate endDate) {
        try {
            List<Event> eventList = eventRepository.findByCreatedAtBetween(startDate,endDate);
            return eventList;
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public List<EventReview> getReview(String eventName) {
        try{
            List<Event> event = eventRepository.findByName(eventName);
            List<EventReview> eventReviewList = eventReviewRepository.findByEvent(event.get(0));

            return eventReviewList;
        }catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
}
