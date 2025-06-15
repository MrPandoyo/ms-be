package com.example.ms_be.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RoleMenuDto {

    private Long roleId;
    private Set<Long> menuIds;

}
