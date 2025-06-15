package com.example.ms_be.config;

import com.example.ms_be.dao.MasterUserDao;
import com.example.ms_be.entity.MasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Configuration
public class AuditingConfig {

    @Autowired private MasterUserDao masterUserDao;

    @Bean
    public AuditorAware<MasterUser> auditorAware() { // Changed return type to MasterUser
        return () -> { // Changed return type to MasterUser
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
                return Optional.empty();
            }

            // NEW: Directly cast the principal to MasterUser
            // This assumes JwtUserDetailsService returns MasterUser directly.
            Object principal = authentication.getPrincipal();
            if (principal instanceof MasterUser) {
                return Optional.of((MasterUser) principal);
            }
            return Optional.empty(); // Should not happen if configured correctly
        };
    }
}
