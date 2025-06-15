package com.example.ms_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
@Table
public class RoleMenu extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleMenuId;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    private MasterRole role;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    @JsonBackReference
    private MasterMenu menu;

    public RoleMenu() {
    }

    public RoleMenu(MasterRole role, MasterMenu menu) {
        this.role = role;
        this.menu = menu;
    }
}
