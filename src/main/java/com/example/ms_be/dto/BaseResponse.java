package com.example.ms_be.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class BaseResponse {

    private String status;
    private String message;
    private Object data;

}
