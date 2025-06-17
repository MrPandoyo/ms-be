package com.example.ms_be.service;

import com.example.ms_be.dao.MasterMenuDao;
import com.example.ms_be.dao.MasterRoleDao;
import com.example.ms_be.dao.RoleMenuDao;
import com.example.ms_be.dto.RoleMenuDto;
import com.example.ms_be.entity.MasterMenu;
import com.example.ms_be.entity.MasterRole;
import com.example.ms_be.entity.RoleMenu;
import io.github.perplexhub.rsql.RSQLJPASupport;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MasterRoleService {

    @Autowired private MasterRoleDao masterRoleDao;
    @Autowired private MasterMenuDao masterMenuDao;
    @Autowired private RoleMenuDao roleMenuDao;

    public List<MasterRole> getRoles() {
        return masterRoleDao.findAll();
    }

    @Transactional
    public void save(RoleMenuDto roleMenuDto) throws Exception {
        Long roleId = roleMenuDto.getRoleId();
        Set<Long> menuIds = roleMenuDto.getMenuIds();

        // 1. Fetch the Student
        Optional<MasterRole> roleOptional = masterRoleDao.findById(roleId);
        if (roleOptional.isEmpty()) {
            throw new IllegalArgumentException("Role with ID " + roleId + " not found.");
        }
        MasterRole role = roleOptional.get();

        // 2. Fetch all Menu from the provided IDs
        List<MasterMenu> menuList = masterMenuDao.findAllById(menuIds);
        if (menuList.size() != menuIds.size()) {
            // Handle case where some course IDs are not found
            Set<Long> foundCourseIds = menuList.stream()
                    .map(MasterMenu::getMenuId)
                    .collect(Collectors.toSet());
            List<Long> missingCourseIds = menuIds.stream()
                    .filter(id -> !foundCourseIds.contains(id))
                    .toList();
            throw new IllegalArgumentException("One or more menu not found with IDs: " + missingCourseIds);
        }

        // 3. Determine existing roleMenu for the role
        Set<RoleMenu> existingRoleMenus = role.getRoleMenus();
        Set<Long> existingMenuIds = existingRoleMenus.stream()
                .map(sc -> sc.getMenu().getMenuId())
                .collect(Collectors.toSet());

        // 4. Identify new RoleMenu and RoleMenu to remove

        // Menu to add (new favorites)
        List<MasterMenu> newMenu = menuList.stream()
                .filter(menu -> !existingMenuIds.contains(menu.getMenuId()))
                .toList();

        // RoleMenu to remove (no longer favorites)
        List<RoleMenu> roleMenuToRemove = existingRoleMenus.stream()
                .filter(menu -> !menuIds.contains(menu.getMenu().getMenuId()))
                .toList();

        // 5. Remove old roleMenu (no longer favorite)
        for (RoleMenu rm : roleMenuToRemove) {
            // Important: Remove from both sides to maintain bidirectionality
            role.removeRoleMenu(rm);
            rm.getMenu().removeRoleMenu(rm); // Important for orphanRemoval
            roleMenuDao.delete(rm);
        }

        // 6. Add new roleMenu (new favorites)
        for (MasterMenu menu : newMenu) {
            // In a real application, you might get these from the form as well.
            RoleMenu newRoleMenu = new RoleMenu(role, menu);

            // Important: Add to both sides to maintain bidirectionality
            role.addRoleMenu(newRoleMenu);
            menu.addRoleMenu(newRoleMenu);
            roleMenuDao.save(newRoleMenu);
        }
    }

}
