package com.example.ms_be.dto;

import com.example.ms_be.constant.Status;
import com.example.ms_be.entity.MasterMenu;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MenuDto {

    private Long menuId;
    private Long parentId;
    private String menuName;
    private String menuCode;
    private String menuIcon;
    private String menuUrl;
    private Long menuOrder;
    private Status status;

    private List<MenuDto> subMenu;

    public MenuDto() {
    }

    public MenuDto(MasterMenu masterMenu) {
        this.menuId = masterMenu.getMenuId();
        this.parentId = masterMenu.getParent() != null ? masterMenu.getParent().getMenuId() : null;
        this.menuCode = masterMenu.getMenuCode();
        this.menuName = masterMenu.getMenuName();
        this.menuIcon = masterMenu.getMenuIcon();
        this.menuUrl = masterMenu.getMenuUrl();
        this.menuOrder = masterMenu.getMenuOrder();
        this.status = masterMenu.getStatus();
        this.subMenu = new ArrayList<>();
    }
}
