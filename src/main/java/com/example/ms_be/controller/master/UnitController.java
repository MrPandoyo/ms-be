package com.example.ms_be.controller.master;

import com.example.ms_be.dto.BaseResponse;
import com.example.ms_be.service.MasterUnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/unit")
public class UnitController {

    @Autowired private MasterUnitService masterUnitService;

    @GetMapping
    public ResponseEntity<?> getMasterUnit(@RequestParam(required = false) String search, @PageableDefault Pageable pageable) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            baseResponse.setStatus("success");
            baseResponse.setData(masterUnitService.getMasterUnits(search, pageable));
        }catch (Exception e){
            log.error("<UNK>", e);
            baseResponse.setStatus("01");
            baseResponse.setMessage(e.getMessage());
        }
        return ResponseEntity.ok(baseResponse);
    }

}
