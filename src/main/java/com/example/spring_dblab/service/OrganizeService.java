package com.example.spring_dblab.service;

import com.example.spring_dblab.dto.EventDto;
import com.example.spring_dblab.dto.EventEditDto;
import com.example.spring_dblab.entitiy.Event;
import com.example.spring_dblab.entitiy.User;
import com.example.spring_dblab.entitiy.UserAlarm;
import com.example.spring_dblab.repository.EventRepository;
import com.example.spring_dblab.repository.UserAlarmRepository;
import com.example.spring_dblab.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.spring_dblab.utils.SecurityUtil.getCurrentMemberId;

@Service
@RequiredArgsConstructor
public class OrganizeService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final UserAlarmRepository userAlarmRepository;
    private final EmailService emailService;

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

            List<UserAlarm> userAlarmList = userAlarmRepository.findAll();

            for(UserAlarm i : userAlarmList) {
                if(description.contains(i.getWord())){
                    User alarmUser = i.getUser();
                    this.emailService.sendMail(alarmUser.getEmail(),i.getWord());
                }
            }

            return "success";
        } catch (Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String editEvent(EventEditDto eventEditDto){
        try {
            long eventCode = eventEditDto.getEventCode();

            Optional<Event> originalEvent = eventRepository.findById(eventCode);

            if(originalEvent.isPresent()) {
                Event t = originalEvent.get();
                String name = eventEditDto.getName();
                String description = eventEditDto.getDescription();
                String userEmail = getCurrentMemberId();
                Optional<User> user = userRepository.findByEmail(userEmail);

                if(user.isPresent()&&user.get()==t.getUser()){
                    t.setName(name);
                    t.setDescription(description);
                    eventRepository.save(t);
                }
                return "success";
            }

            return "fail";
        } catch(Exception err) {
            err.printStackTrace();
            throw err;
        }
    }

    public String deleteEvent(EventEditDto eventEditDto) {
        long eventCode = eventEditDto.getEventCode();

        Optional<Event> originalEvent = eventRepository.findById(eventCode);

        if(originalEvent.isPresent()) {
            Event t = originalEvent.get();
            String userEmail = getCurrentMemberId();
            Optional<User> user = userRepository.findByEmail(userEmail);

            if(user.isPresent()&&user.get()==t.getUser()){
                eventRepository.delete(t);
            }
            return "success";
        }

        return "fail";
    }
}
