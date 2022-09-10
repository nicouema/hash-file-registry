package com.bcnicouema.archivos.exception.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND("404"),
    BAD_REQUEST("400");
    private final String defaultMessage;
    }
