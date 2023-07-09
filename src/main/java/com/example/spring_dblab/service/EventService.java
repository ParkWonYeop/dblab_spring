package com.example.spring_dblab.service;

import com.example.spring_dblab.model.Event;
import com.example.spring_dblab.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getEvent() {
        try {
            List<Event> eventList = eventRepository.findAll();
            return eventList;
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
}
