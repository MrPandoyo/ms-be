package com.example.ms_be.dao;

import com.example.ms_be.entity.MasterUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MasterUnitDao extends JpaRepository<MasterUnit, Long>, JpaSpecificationExecutor<MasterUnit> {
}
