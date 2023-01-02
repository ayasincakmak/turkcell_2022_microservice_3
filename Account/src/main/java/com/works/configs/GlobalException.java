package com.works.configs;

import com.works.utils.REnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity methodArgumentNotValid( MethodArgumentNotValidException ex ) {
        Map<REnum, Object> map = new HashMap();
        map.put(REnum.status, false);
        map.put(REnum.errors, parseError(ex.getFieldErrors()));
        return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
    }

    private List parseError(List<FieldError> fieldErrors) {
        List ls = new ArrayList();
        for ( FieldError item : fieldErrors ) {
            Map map = new HashMap();
            map.put("field", item.getField());
            map.put("message", item.getDefaultMessage());
            ls.add(map);
        }
        return ls;
    }

}
