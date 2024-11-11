package com.gabriel.desafiopicpay.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {

    private String timestamp;
    private String path;
    private int status;
    private String error;
    private String message;
    List<ErrorFieldDTO> erros;

}
