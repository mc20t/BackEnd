package org.example.Lesha_REST.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    public static Map<?, ?> buildErrorResponse(int code, String msg){
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("code", String.valueOf(code));
        response.put("msg", msg);
        return response;
    }

    public static ResponseEntity<Map<?, ?>> logAndSendErrorResponse(Logger logger, HttpStatus status, String msg){
        logger.log(Level.WARNING, msg);
        return ResponseEntity.status(status).
                body(Utils.buildErrorResponse(status.value(), msg));
    }

    public static Map<?, ?> deserializeJsonOrNull(String json){
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String,Object>> typeRef
                = new TypeReference<>() {
        };

        try {
            return mapper.readValue(json, typeRef);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
