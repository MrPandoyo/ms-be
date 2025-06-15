package com.example.ms_be.entity;

import com.example.ms_be.dto.MenuDto;
import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

@Getter @Setter
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) // NEW: Prevents issues with lazy loading proxies during serialization
public class MasterMenu extends BaseEntity {

    @JsonIgnore
    @CreatedBy
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "created_by", updatable = false)
    private MasterUser createdBy;

    @JsonIgnore
    @LastModifiedBy
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private MasterUser updatedBy;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    private MasterMenu parent;

    @Column(length = 50, nullable = false, unique = true)
    private String menuCode;

    @Column(length = 100, nullable = false)
    private String menuName;

    @Column(length = 100)
    private String menuIcon;

    private String menuUrl;

    private Long menuOrder = 0L;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RoleMenu> roleMenus;

    public MasterMenu() {
    }

    public MasterMenu(MenuDto menuDto) {
        this.menuId = menuDto.getMenuId();
        this.menuCode = menuDto.getMenuCode();
        this.menuName = menuDto.getMenuName();
        this.menuIcon = menuDto.getMenuIcon();
        this.menuUrl = menuDto.getMenuUrl();
        this.menuOrder = menuDto.getMenuOrder();
    }

    // Helper method to add a roleMenu
    public void addRoleMenu(RoleMenu roleMenu) {
        roleMenus.add(roleMenu);
        roleMenu.setMenu(this);
    }

    public void removeRoleMenu(RoleMenu roleMenu) {
        roleMenus.remove(roleMenu);
        roleMenu.setMenu(null);
    }

    // NEW: Custom equals and hashCode based on ID for JPA entities
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MasterMenu that = (MasterMenu) o;
        return Objects.equals(menuId, that.menuId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menuId);
    }

}
