package com.bcnicouema.archivos.repository;

import com.bcnicouema.archivos.model.Registry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistryRepository extends JpaRepository<Registry, String> {
}
