package com.bcnicouema.archivos.exception.handler;

import com.bcnicouema.archivos.exception.BadRequestException;
import com.bcnicouema.archivos.exception.NotFoundException;
import com.bcnicouema.archivos.exception.error.ErrorCode;
import com.bcnicouema.archivos.exception.error.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorDetails> handleNotFound(NotFoundException ex, HttpServletRequest request) {

        ErrorDetails error = ErrorDetails.builder()
                .status(ErrorCode.NOT_FOUND)
                .timestamp(LocalDateTime.now())
                .message(ex.getMessage())
                .path(request.getRequestURL().toString())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<ErrorDetails> handleBadRequest(BadRequestException ex, HttpServletRequest request) {

        ErrorDetails error = ErrorDetails.builder()
                .status(ErrorCode.BAD_REQUEST)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURL().toString())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
