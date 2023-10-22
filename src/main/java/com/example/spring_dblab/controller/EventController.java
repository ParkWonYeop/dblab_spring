package com.example.spring_dblab.controller;

import com.example.spring_dblab.entitiy.Event;
import com.example.spring_dblab.entitiy.EventReview;
import com.example.spring_dblab.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
        return this.eventService.getEvent();
    }

    @GetMapping("/name")
    public List<Event> findEvent(@RequestParam("name") String eventName) {
        return this.eventService.findEvent(eventName);
    }

    @GetMapping("/date")
    public List<Event> findEventByDate(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
        LocalDate startDateParse = LocalDate.parse(startDate);
        LocalDate endDateParse = LocalDate.parse(endDate);

        return this.eventService.findEventByDate(startDateParse, endDateParse);
    }

    @GetMapping("/review")
    public List<EventReview> getReview(@RequestParam("eventName") String eventName) {
        return this.eventService.getReview(eventName);
    }
}
