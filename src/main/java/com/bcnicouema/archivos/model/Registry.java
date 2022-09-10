package com.bcnicouema.archivos.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "registry")
public class Registry{

    @Id
    @Column(name = "fileName", unique = true, nullable = false)
    private String fileName;

    @Column(name = "\"hash-sha-256\"", length = 64)
    private String hashSha256;

    @Column(name = "\"hash-sha-512\"", length = 128)
    private String hashSha512;

    @Column(name = "lastUpload")
    @DateTimeFormat(pattern = "yyyy-MM-dd\"T\"HH:mm:ss.SS\"Z\"")
    private LocalDateTime lastUpload;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Registry registry = (Registry) o;
        return Objects.equals(fileName, registry.fileName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName);
    }
}
