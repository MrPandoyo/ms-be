package com.example.ms_be.dao;

import com.example.ms_be.entity.MasterUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MasterUserDao extends JpaRepository<MasterUser, Long> {
    Optional<MasterUser> findByUsername(String username);
}
