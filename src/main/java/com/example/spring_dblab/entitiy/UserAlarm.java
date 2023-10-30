package com.example.spring_dblab.entitiy;

import com.example.spring_dblab.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserAlarm {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(nullable = false)
    private String word;

    @Builder
    public UserAlarm(User user, String word) {
        this.user = user;
        this.word = word;
    }
}
