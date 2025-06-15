package com.example.ms_be.dao;

import com.example.ms_be.entity.MasterMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MasterMenuDao extends JpaRepository<MasterMenu, Long>, JpaSpecificationExecutor<MasterMenu> {
}
