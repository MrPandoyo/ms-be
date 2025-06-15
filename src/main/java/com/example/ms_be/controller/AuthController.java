package com.example.ms_be.controller;

import com.example.ms_be.constant.Status;
import com.example.ms_be.dao.MasterUserDao;
import com.example.ms_be.dao.UserSessionsDao;
import com.example.ms_be.dto.BaseResponse;
import com.example.ms_be.dto.LoginRequest;
import com.example.ms_be.dto.LoginResponse;
import com.example.ms_be.entity.MasterUser;
import com.example.ms_be.entity.UserSessions;
import com.example.ms_be.service.JwtUserDetailService;
import com.example.ms_be.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtUserDetailService jwtUserDetailService;

    @Autowired
    private MasterUserDao masterUserDao;

    @Autowired
    private UserSessionsDao userSessionsDao;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) throws Exception {
        BaseResponse baseResponse = new BaseResponse();

        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailService
                .loadUserByUsername(loginRequest.getUsername());

        final String token = jwtUtil.generateToken(userDetails);

        Optional<MasterUser> optionalMasterUser = masterUserDao.findByUsername(userDetails.getUsername());
        if (optionalMasterUser.isPresent()) {

            // Save session details to the database
            UserSessions session = new UserSessions();
            session.setUser(optionalMasterUser.get());
            session.setSessionToken(token);
            session.setLoginAt(LocalDateTime.now());
            session.setIpAddress(request.getRemoteAddr());
            session.setUserAgent(request.getHeader("User-Agent"));
            session.setStatus(Status.ACTIVE);
            userSessionsDao.save(session);

            baseResponse.setStatus("success");
            baseResponse.setMessage("Login successful");
            baseResponse.setData(new LoginResponse(optionalMasterUser.get(), token));
        }
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization") String authorizationHeader) {
        BaseResponse baseResponse = new BaseResponse();
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);

            // Update session status in the database
            Optional<UserSessions> sessionOptional = userSessionsDao.findBySessionTokenAndStatus(jwtToken, Status.ACTIVE);
            if (sessionOptional.isPresent()) {
                UserSessions session = sessionOptional.get();
                session.setLogoutAt(LocalDateTime.now());
                session.setStatus(Status.INACTIVE);
                userSessionsDao.save(session);

                baseResponse.setStatus("success");
                baseResponse.setMessage("Logout Successful");
                return ResponseEntity.ok(baseResponse);
            } else {
                return ResponseEntity.badRequest().body("No active session found for this token.");
            }
        }
        return ResponseEntity.badRequest().body("Invalid or missing Authorization header.");
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

}
