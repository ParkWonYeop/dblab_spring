package com.example.spring_dblab.repository;

import com.example.spring_dblab.entitiy.UserAlarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAlarmRepository extends JpaRepository<UserAlarm,Long> {
}
