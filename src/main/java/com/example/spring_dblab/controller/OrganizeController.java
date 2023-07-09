package com.example.spring_dblab.controller;

import com.example.spring_dblab.dto.EventDto;
import com.example.spring_dblab.dto.SignupDto;
import com.example.spring_dblab.service.OrganizeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/organizer")
public class OrganizeController {
    OrganizeService organizeService;

    public OrganizeController(OrganizeService organizeService){
        this.organizeService = organizeService;
    }


    @PostMapping("/event")
    public String addEvent(@RequestBody EventDto eventDto) throws Exception {
        try{
            return organizeService.addEvent(eventDto);
        } catch(Exception e) {
            throw e;
        }
    }

    @GetMapping()
    public String authOrganizer() {

        return "ok";
    }
}
