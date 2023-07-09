package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.EventDto;
import com.example.spring_dblab.model.Event;
import com.example.spring_dblab.model.User;
import com.example.spring_dblab.repository.EventRepository;
import com.example.spring_dblab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.spring_dblab.utils.SecurityUtil.getCurrentMemberId;

@Service
public class OrganizeService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrganizeService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
    }

    public String addEvent(EventDto eventDto) throws Exception {
        try {
            String name = eventDto.getName();
            String description = eventDto.getDescription();
            String userEmail = getCurrentMemberId();
            Optional<User> user = userRepository.findByEmail(userEmail);

            if(user.isPresent()) {
                Event event = new Event(name,description,user.get());
                eventRepository.save(event);
            } else {
                throw new Exception("User not Found");
            }

            return "success";
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }
}
