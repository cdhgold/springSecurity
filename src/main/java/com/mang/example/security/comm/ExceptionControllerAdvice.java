package com.mang.example.security.comm;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
 

@ControllerAdvice
public class ExceptionControllerAdvice {

    /** The logger. */
    Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);
    
    @SuppressWarnings("rawtypes")
    @ExceptionHandler({ ControllerException.class})
    public ResponseEntity exception(Exception e) {
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        logger.error("ExceptionControllerAdvice==>",e);
        rtnMap.put("message", e.getMessage()+"");
        return ResponseEntity.badRequest().body(rtnMap);
    }
}