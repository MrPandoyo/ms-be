package com.example.ms_be.service;

import com.example.ms_be.dao.MasterMenuDao;
import com.example.ms_be.dto.MenuDto;
import com.example.ms_be.entity.MasterMenu;
import io.github.perplexhub.rsql.RSQLJPASupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class MasterMenuService {

    @Autowired
    private MasterMenuDao masterMenuDao;

    public Page<MasterMenu> getMasterMenus(String rsqlQuery,Pageable pageable) {
        if (rsqlQuery == null || rsqlQuery.trim().isEmpty()) {
            return masterMenuDao.findAll(pageable);
        }
        return masterMenuDao.findAll(RSQLJPASupport.toSpecification(rsqlQuery),pageable);
    }

    public void save(MenuDto menuDto) throws Exception {
        Optional<MasterMenu> parentMenu = menuDto.getParentId() != null ? masterMenuDao.findById(menuDto.getParentId()) : Optional.empty();

        MasterMenu masterMenu = new MasterMenu(menuDto);
        if(menuDto.getMenuId() != null) {
            Optional<MasterMenu> optionalMasterMenu = masterMenuDao.findById(menuDto.getMenuId());
            if(optionalMasterMenu.isEmpty()) {throw new Exception("Menu with ID {} not found");}

            optionalMasterMenu.get().setMenuName(menuDto.getMenuName());
            optionalMasterMenu.get().setMenuCode(menuDto.getMenuCode());
            optionalMasterMenu.get().setMenuIcon(menuDto.getMenuIcon());
            optionalMasterMenu.get().setMenuUrl(menuDto.getMenuUrl());
            optionalMasterMenu.get().setMenuOrder(menuDto.getMenuOrder());
            masterMenu = optionalMasterMenu.get();
        }
        masterMenu.setParent(parentMenu.orElse(masterMenu.getParent()));
        masterMenuDao.save(masterMenu);
    }

}
