package com.example.ms_be.service;

import com.example.ms_be.dao.MasterUserDao;
import com.example.ms_be.entity.MasterUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private MasterUserDao masterUserDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<MasterUser> masterUser = masterUserDao.findByUsername(username);
        if(masterUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        MasterUser user = masterUser.get();
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getRoleCode()));
//
//        return new User(user.getUsername(), user.getPassword(), authorities);
        return masterUser.get();
    }

}
