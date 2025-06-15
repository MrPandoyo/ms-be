package com.example.ms_be.dto;

import com.example.ms_be.entity.MasterUser;
import com.example.ms_be.helper.RoleMenuConverter;
import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {

    private Long userId;
    private Long roleId;
    private String username;
    private String fullName;
    private String token;

    private List<MenuDto> menu;

    public LoginResponse() {
    }

    public LoginResponse(MasterUser masterUser, String token) {
        this.userId = masterUser.getUserId();
        this.username = masterUser.getUsername();
        this.fullName = masterUser.getFullName();
        this.roleId = masterUser.getRole().getRoleId();
        this.token = token;
        this.menu = RoleMenuConverter.convertToNestedList(masterUser.getRole().getRoleMenus());
    }


}
