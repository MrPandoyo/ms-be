package com.example.ms_be.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Getter @Setter
@Entity
@Table
@EntityListeners(AuditingEntityListener.class)
public class MasterUnit extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long unitId;

    @Column(length = 50, nullable = false, unique = true)
    private String unitCode;

    @Column(length = 100, nullable = false)
    private String unitName;

    private String description;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "parent_unit_id")
    private MasterUnit parentUnit;

    private String address;

    @Column(length = 20)
    private String phone;

    @Column(length = 100)
    private String email;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private MasterUser manager;

    @CreatedBy
    @ManyToOne
    @JoinColumn(name = "created_by", updatable = false)
    private MasterUser createdBy;

    @LastModifiedBy
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private MasterUser updatedBy;

    @OneToMany(mappedBy = "unitId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<MasterUnit> children;
}
