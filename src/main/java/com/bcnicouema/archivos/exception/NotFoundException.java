package com.bcnicouema.archivos.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NotFoundException extends RuntimeException{
    private final String message;
}
