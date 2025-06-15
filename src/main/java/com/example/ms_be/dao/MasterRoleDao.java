package com.example.ms_be.dao;

import com.example.ms_be.entity.MasterRole;
import com.example.ms_be.entity.MasterUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MasterRoleDao extends JpaRepository<MasterRole, Long>, JpaSpecificationExecutor<MasterRole> {
}
