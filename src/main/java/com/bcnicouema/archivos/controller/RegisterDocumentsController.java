package com.bcnicouema.archivos.controller;

import com.bcnicouema.archivos.dto.mapper.RegistryControllerMapper;
import com.bcnicouema.archivos.dto.response.Registry256ListResponse;
import com.bcnicouema.archivos.dto.response.Registry512ListResponse;
import com.bcnicouema.archivos.dto.response.RegistrySha256Response;
import com.bcnicouema.archivos.dto.response.RegistrySha512Response;
import com.bcnicouema.archivos.exception.BadRequestException;
import com.bcnicouema.archivos.exception.NotFoundException;
import com.bcnicouema.archivos.model.Registry;
import com.bcnicouema.archivos.service.RegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/documents")
public class RegisterDocumentsController {

    private final RegistryService registryService;

    private final RegistryControllerMapper mapper;

    @PostMapping
    public ResponseEntity<?> registerFiles(@RequestParam("hashType") String hashType,
                                           @RequestParam("files") MultipartFile[] files){

        if (!(Arrays.stream(files).findFirst().get().getSize() > 0)) {
            throw new BadRequestException("No se subieron documentos");
        }

        if (hashType.equals("SHA-256")){
            List<Registry> registries = registryService.registerDocumentsSHA256(files);

            List<RegistrySha256Response> content = mapper.registryListToRegistry256ListResponse(registries);
            Registry256ListResponse response;
            response = Registry256ListResponse.builder()
                    .ShaHash(hashType)
                    .registryResponseList(content)
                    .build();

            return ResponseEntity.ok(response);

        } else if (hashType.equals("SHA-512")) {
            List<Registry> registries = registryService.registerDocumentsSHA512(files);

            List<RegistrySha512Response> content = mapper.registryListToRegistry512ListResponse(registries);
            Registry512ListResponse response;
            response = Registry512ListResponse.builder()
                    .ShaHash(hashType)
                    .registryResponseList(content)
                    .build();

            return ResponseEntity.ok(response);

        } else {
            throw new BadRequestException("El parámetro ‘hash’ solo puede ser ‘SHA-256’ o ‘SHA-512’");
        }
    }

    @GetMapping("/{hash}")
    public ResponseEntity<?> getRegistryByHash(@RequestParam String hashType,
                                               @PathVariable String hash) {
        if (hashType.equals("SHA-256")) {

            Registry registry = registryService.getRegistryByHash256(hash);
            RegistrySha256Response response = mapper.registryToRegistry256Response(registry);
            return ResponseEntity.ok(response);

        }else if (hashType.equals("SHA-512")){

            Registry registry = registryService.getRegistryByHash512(hash);
            RegistrySha512Response response = mapper.registryToRegistry512Response(registry);
            return ResponseEntity.ok(response);
        }else {
            throw new BadRequestException("El parámetro ‘hash’ solo puede ser ‘SHA-256’ o ‘SHA-512’");
        }

    }

    @GetMapping
    public ResponseEntity<List<Registry>> getAllRegistries() {
        List<Registry> content = registryService.getAllRegistries();
        return ResponseEntity.ok(content);
    }
}
