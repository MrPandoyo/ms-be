package com.example.ms_be.service;

import com.example.ms_be.dao.MasterUnitDao;
import com.example.ms_be.entity.MasterUnit;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MasterUnitService {

    @Autowired
    private MasterUnitDao masterUnitDao;

    public Page<MasterUnit> getMasterUnits(String rsqlQuery,Pageable pageable) {
        if (rsqlQuery == null || rsqlQuery.trim().isEmpty()) {
            return masterUnitDao.findAll(pageable);
        }
        return masterUnitDao.findAll(RSQLJPASupport.toSpecification(rsqlQuery),pageable);
    }


}
