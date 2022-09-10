package com.bcnicouema.archivos.dto.mapper;

import com.bcnicouema.archivos.dto.response.RegistrySha256Response;
import com.bcnicouema.archivos.dto.response.RegistrySha512Response;
import com.bcnicouema.archivos.model.Registry;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper
public interface RegistryControllerMapper {

    @Named("registryToRegistry256Response")
    @Mapping(target = "lastUpload", source = "lastUpload")
    RegistrySha256Response registryToRegistry256Response(Registry  registry);

    @Named("registryToRegistry512Response")
    @Mapping(target = "lastUpload", source = "lastUpload")
    RegistrySha512Response registryToRegistry512Response(Registry  registry);

    @IterableMapping(qualifiedByName = "registryToRegistry256Response")
    List<RegistrySha256Response> registryListToRegistry256ListResponse(List<Registry> list);

    @IterableMapping(qualifiedByName = "registryToRegistry512Response")
    List<RegistrySha512Response> registryListToRegistry512ListResponse(List<Registry> list);

}
