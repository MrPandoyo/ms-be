package com.example.ms_be.controller.master;

import com.example.ms_be.dao.MasterRoleDao;
import com.example.ms_be.dto.BaseResponse;
import com.example.ms_be.dto.RoleMenuDto;
import com.example.ms_be.helper.RoleMenuConverter;
import com.example.ms_be.service.MasterRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class RoleController {

    @Autowired private MasterRoleDao masterRoleDao;
    @Autowired private MasterRoleService masterRoleService;

    @GetMapping("/role/{roleId}/menu")
    public ResponseEntity<?> getRoleMenus(@PathVariable("roleId") Long roleId) {
        BaseResponse baseResponse = new BaseResponse();
        masterRoleDao.findById(roleId).ifPresent(role -> {
            baseResponse.setStatus("success");
            baseResponse.setData(RoleMenuConverter.convertToNestedList(role.getRoleMenus()));
        });

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/role-menu")
    public ResponseEntity<?> saveRole(@RequestBody RoleMenuDto roleMenuDto) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            masterRoleService.save(roleMenuDto);

            baseResponse.setStatus("success");
            baseResponse.setMessage("Menu created successfully");
        }catch (Exception e){
            log.error("<UNK>", e);
            baseResponse.setStatus("01");
            baseResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(baseResponse);
    }

}
