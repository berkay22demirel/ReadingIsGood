package com.berkay22demirel.readingisgood.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;
import java.util.Map;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response {

    private String status;
    private String responseMessage;
    private Map<String, String> validationErrors;
    private Object data;
    private long systemTime = new Date().getTime();

    public Response(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public Response(String responseMessage, Map<String, String> validationErrors) {
        this.responseMessage = responseMessage;
        this.validationErrors = validationErrors;
    }

    public Response(String responseMessage, Object data) {
        this.responseMessage = responseMessage;
        this.data = data;
    }

    public Response(Object data) {
        this.data = data;
    }
}
