package com.bcnicouema.archivos.exception.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"timestamp", "status", "message", "path"})
public class ErrorDetails {

    @NotNull
    LocalDateTime timestamp;

    @NotNull
    ErrorCode status;

    String message;

    String path;

}
