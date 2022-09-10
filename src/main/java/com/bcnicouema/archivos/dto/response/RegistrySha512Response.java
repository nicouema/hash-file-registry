package com.bcnicouema.archivos.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrySha512Response {

    private String fileName;

    @JsonProperty("hash")
    private String hashSha512;

    @DateTimeFormat(pattern = "yyyy-MM-dd\"T\"HH:mm:ss.SSS\"Z\"")
    @JsonProperty("lastUpload")
    private LocalDateTime lastUpload;

}

