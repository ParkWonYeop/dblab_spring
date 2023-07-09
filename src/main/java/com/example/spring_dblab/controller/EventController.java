package com.example.spring_dblab.controller;

import com.example.spring_dblab.dto.EventDto;
import com.example.spring_dblab.model.Event;
import com.example.spring_dblab.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<Event> getEvent() {
        return eventService.getEvent();
    }
}
