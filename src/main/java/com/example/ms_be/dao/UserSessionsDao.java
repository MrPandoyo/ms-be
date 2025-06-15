package com.example.ms_be.dao;

import com.example.ms_be.constant.Status;
import com.example.ms_be.entity.UserSessions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserSessionsDao extends JpaRepository<UserSessions, Long> {
    Optional<UserSessions> findBySessionTokenAndStatus(String sessionToken, Status status);
//    Optional<UserSessions> findByUserIdAndSessionTokenAndStatus(Long userId, String sessionToken, String status);
}
