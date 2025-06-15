package com.example.ms_be.entity;

import com.example.ms_be.constant.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter @Setter
@Entity
@Table
public class UserSessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sessionId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private MasterUser user;

    private String sessionToken;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginAt = LocalDateTime.now();

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logoutAt;

    @Column(length = 50)
    private String ipAddress;

    private String userAgent;

    @Enumerated(EnumType.STRING)
    private Status status;
}
