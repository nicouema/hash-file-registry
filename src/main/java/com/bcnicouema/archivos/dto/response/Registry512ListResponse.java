package com.bcnicouema.archivos.dto.response;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Registry512ListResponse {

    @JsonProperty("algorithm")
    private String ShaHash;

    @JsonProperty("documents")
    private List<RegistrySha512Response> registryResponseList;
}
