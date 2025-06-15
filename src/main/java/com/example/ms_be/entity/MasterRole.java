package com.example.ms_be.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
@Entity
@Table
public class MasterRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(length = 50, nullable = false)
    private String roleCode;

    @Column(length = 100, nullable = false)
    private String roleName;

    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleMenu> roleMenus;

    // Helper method to add a roleMenu
    public void addRoleMenu(RoleMenu roleMenu) {
        roleMenus.add(roleMenu);
        roleMenu.setRole(this);
    }

    public void removeRoleMenu(RoleMenu roleMenu) {
        roleMenus.remove(roleMenu);
        roleMenu.setRole(null);
    }

}
