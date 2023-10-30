package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.AlarmDto;
import com.example.spring_dblab.dto.EventDto;
import com.example.spring_dblab.dto.ReviewDto;
import com.example.spring_dblab.entitiy.Event;
import com.example.spring_dblab.entitiy.EventReview;
import com.example.spring_dblab.entitiy.User;
import com.example.spring_dblab.entitiy.UserAlarm;
import com.example.spring_dblab.repository.EventRepository;
import com.example.spring_dblab.repository.EventReviewRepository;
import com.example.spring_dblab.repository.UserAlarmRepository;
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
    private final UserRepository userRepository;
    private final UserAlarmRepository userAlarmRepository;

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
            List<Event> eventList = eventRepository.findByCreatedAtBetween(startDate.atStartOfDay(), endDate.atStartOfDay());
            return eventList;
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public List<EventReview> getReview(long eventId) throws Exception {
        try{
            Optional<Event> event = eventRepository.findById(eventId);
            if(event.isEmpty()) {
                throw new Exception("Not found event");
            }
            return eventReviewRepository.findByEvent(event.get());
        }catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String setAlarmWord(AlarmDto alarmDto)throws Exception {
        try {
            String word = alarmDto.getWord();
            String userEmail = getCurrentMemberId();
            Optional<User> user = userRepository.findByEmail(userEmail);
            if(user.isPresent()){
                UserAlarm userAlarm = new UserAlarm(user.get(),word);
                userAlarmRepository.save(userAlarm);
            }
            return "success";
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String addReview(ReviewDto reviewDto) {
        try {
            String userEmail = getCurrentMemberId();
            Optional<User> user = userRepository.findByEmail(userEmail);
            Optional<Event> event = eventRepository.findById(reviewDto.getEventId());
            if(user.isPresent()&&event.isPresent()){
                EventReview eventReview = new EventReview(user.get(),event.get(),reviewDto.getReview(),reviewDto.getScore());
                eventReviewRepository.save(eventReview);
            }
            return "success";
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
}
