package com.example.ms_be.helper;

import com.example.ms_be.dto.MenuDto;
import com.example.ms_be.entity.RoleMenu;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class RoleMenuConverter {

    public static List<MenuDto> convertToNestedList(Set<RoleMenu> roleMenus) {
        // Create a map of id to node for easy access.
        Map<Long, MenuDto> nodeMap = roleMenus.stream().collect(Collectors.toMap(roleMenu -> roleMenu.getMenu().getMenuId(), roleMenu -> new MenuDto(roleMenu.getMenu())));
        List<MenuDto> rootNodes = new ArrayList<>();

        for (RoleMenu roleMenu : roleMenus) {
            MenuDto currentNode = nodeMap.get(roleMenu.getMenu().getMenuId());
            if (roleMenu.getMenu().getParent() == null) {
                // If the object has no parent, it's a root node.
                rootNodes.add(currentNode);
            } else {
                // If the object has a parent, find the parent and add it as a child.
                MenuDto parent = nodeMap.get(roleMenu.getMenu().getParent().getMenuId());
                if (parent != null) {
                    parent.getSubMenu().add(currentNode);
                }
            }
        }
        return rootNodes;
    }

}
