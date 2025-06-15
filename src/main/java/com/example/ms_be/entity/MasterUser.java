package com.example.ms_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter @Setter
@Entity
@Table
public class MasterUser extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 50, nullable = false, unique = true)
    private String username;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @Column(length = 100)
    private String fullName;

    @Column(length = 20)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private MasterRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private MasterUnit unitId;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    private MasterUser createdBy;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private MasterUser updatedBy;


    // UserDetails interface implementations (NEW)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // For simplicity, granting a default "ROLE_USER"
        return Collections.singletonList(new SimpleGrantedAuthority(this.role.getRoleCode()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
