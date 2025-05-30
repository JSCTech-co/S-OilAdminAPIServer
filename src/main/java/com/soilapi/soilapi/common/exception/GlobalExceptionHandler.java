package com.soilapi.soilapi.common.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
     // 일반 런타임 예외 처리
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        ex.printStackTrace();
        
        Map<String, Object> error = new HashMap<>();
        error.put("message", ex.getMessage());
        error.put("error", "RuntimeException");
        error.put("status", 500);
        error.put("timestamp", LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    // 특정 커스텀 예외도 처리 가능
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of(
            "error", "잘못된 요청",
            "message", ex.getMessage()
        ));
    }

    // 모든 예외 catch (최후의 방어선)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleAll(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
            "error", "Unexpected Error",
            "message", ex.getMessage()
        ));
    }
}
