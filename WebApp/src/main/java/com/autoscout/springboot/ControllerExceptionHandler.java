package com.autoscout.springboot;

import com.autoscout.springboot.exception.IncorrectFormatException;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class ControllerExceptionHandler {

    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ResponseStatus(HttpStatus.BAD_REQUEST) // 500
    @ExceptionHandler(IncorrectFormatException.class)
    @ResponseBody
    public String handleIncorrectFormat(IncorrectFormatException ex){

        logger.error(ex.getMessage());
        return ex.getMessage();
    }
}
