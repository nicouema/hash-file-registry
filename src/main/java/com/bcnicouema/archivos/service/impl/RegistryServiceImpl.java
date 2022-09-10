package com.bcnicouema.archivos.service.impl;

import com.bcnicouema.archivos.exception.NotFoundException;
import com.bcnicouema.archivos.model.Registry;
import com.bcnicouema.archivos.repository.RegistryRepository;
import com.bcnicouema.archivos.service.RegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.bcnicouema.archivos.util.ShaHashUtils.hashSha256;
import static com.bcnicouema.archivos.util.ShaHashUtils.hashSha512;

@Service
@RequiredArgsConstructor
public class RegistryServiceImpl implements RegistryService {

    private final RegistryRepository registryRepository;

    @Override
    @Transactional
    public List<Registry> registerDocumentsSHA256(MultipartFile[] list) {
        List<Registry> registries = new ArrayList<>(list.length);
        try {
            Arrays.stream(list).forEach(file -> registries.add(fileToRegistrySHA256(file)));
            return registries;
        }
        catch (Exception ex){
            throw new RuntimeException();
        }
    }

    @Transactional
    private Registry fileToRegistrySHA256(MultipartFile registryToSave) {
        byte[] bytes;
        try {
            bytes = registryToSave.getBytes();
            String hexString = hashSha256(bytes);

            Registry registry = new Registry();
            registry.setFileName(registryToSave.getOriginalFilename());
            registry.setHashSha256(hexString);
            registry = persistRegistry(registry);
            return  registry;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<Registry> registerDocumentsSHA512(MultipartFile[] list) {
        List<Registry> registries = new ArrayList<>(list.length);
        try {
            Arrays.stream(list).forEach(file -> registries.add(fileToRegistrySHA512(file)));
            return registries;
        }
        catch (Exception ex){
            throw new RuntimeException();
        }
    }

    @Transactional
    private Registry fileToRegistrySHA512(MultipartFile file) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
            String hexString = hashSha512(bytes);

            Registry registry = new Registry();
            registry.setFileName(file.getOriginalFilename());
            registry.setHashSha512(hexString);
            registry = persistRegistry(registry);
            return registry;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    private Registry persistRegistry(Registry newRegistry){
        String fileName = newRegistry.getFileName();
        Optional<Registry> registryToPersist = getRegistryIfExist(fileName);
        if (registryToPersist.isEmpty()) {
            registryRepository.save(newRegistry);
            return newRegistry;
        }else {
            Registry registryToUpdate = registryToPersist.get();
            return updateRegistry(registryToUpdate, newRegistry);
        }
    }

    @Transactional
    private Registry updateRegistry(Registry registryToUpdate, Registry newRegistry) {

        if (registryToUpdate.getHashSha256() != null || registryToUpdate.getHashSha512() != null){
            if (newRegistry.getHashSha256() != null){
                if (registryToUpdate.getHashSha256() != null
                        && !newRegistry.getHashSha256().equals(registryToUpdate.getHashSha256())){
                    registryToUpdate.setLastUpload(LocalDateTime.now());
                }
                registryToUpdate.setHashSha256(newRegistry.getHashSha256());
            }else  {
                if (registryToUpdate.getHashSha512() != null
                        && !newRegistry.getHashSha512().equals(registryToUpdate.getHashSha512())){
                    registryToUpdate.setLastUpload(LocalDateTime.now());
                }
                registryToUpdate.setHashSha512(newRegistry.getHashSha512());

            }
        }else {
            registryToUpdate.setLastUpload(LocalDateTime.now());
            if (newRegistry.getHashSha256() != null){
                registryToUpdate.setHashSha256(newRegistry.getHashSha256());
            }else {
                registryToUpdate.setHashSha512(newRegistry.getHashSha512());
            }

        }

        registryRepository.save(registryToUpdate);
        return registryToUpdate;
    }

    private Optional<Registry> getRegistryIfExist(String fileName){
        return registryRepository.findById(fileName);
    }


    @Override
    @Transactional
    public List<Registry> getAllRegistries() {
        return registryRepository.findAll();
    }

    @Override
    public Registry getRegistryByHash256(String hash) {
        List<Registry> list = getAllRegistries();
        for (Registry registry:list) {
            if (registry.getHashSha256() != null){
                if (registry.getHashSha256().equals(hash)){
                    return registry;
                }
            }
        }
        throw new NotFoundException("No hay ningún documento con ese nombre");
    }

    @Override
    public Registry getRegistryByHash512(String hash) {
        List<Registry> list = getAllRegistries();
        for (Registry registry:list) {
            if (registry.getHashSha512() != null){
                if (registry.getHashSha512().equals(hash)){
                    return registry;
                }
            }
        }
        throw new NotFoundException("No hay ningún documento con ese nombre");
    }


}
