package com.bcnicouema.archivos.service;


import com.bcnicouema.archivos.model.Registry;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RegistryService {

    List<Registry> registerDocumentsSHA256(MultipartFile[] list);

    List<Registry> registerDocumentsSHA512(MultipartFile[] list);

    List<Registry> getAllRegistries();

    Registry getRegistryByHash256(String hash);

    Registry getRegistryByHash512(String hash);
}
