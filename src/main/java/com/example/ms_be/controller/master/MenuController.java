package com.example.ms_be.controller.master;

import com.example.ms_be.dto.BaseResponse;
import com.example.ms_be.dto.MenuDto;
import com.example.ms_be.entity.MasterMenu;
import com.example.ms_be.service.MasterMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {

    @Autowired
    private MasterMenuService masterMenuService;

//    @GetMapping
//    public ResponseEntity<?> getMasterMenu(@RequestParam(required = false) String search, @PageableDefault Pageable pageable) {
//        BaseResponse baseResponse = new BaseResponse();
//        try {
//            baseResponse.setStatus("success");
//            baseResponse.setData(masterMenuService.getMasterMenus(search, pageable));
//        }catch (Exception e){
//            log.error("<UNK>", e);
//            baseResponse.setStatus("01");
//            baseResponse.setMessage(e.getMessage());
//        }
//        return ResponseEntity.ok(baseResponse);
//    }

    @PostMapping
    public ResponseEntity<?> saveMenu(@RequestBody MenuDto menuDto) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            masterMenuService.save(menuDto);

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
