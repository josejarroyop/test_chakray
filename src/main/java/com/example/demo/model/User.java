package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @UuidGenerator
    @Column(name ="id", updatable = false, nullable = false)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    @Email(message = "debe ser email valido")
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, length = 255)
    @NotBlank(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Size(min = 10, max = 13, message = "El tax_id debe tener entre 10 y 13 caracteres")
    @Pattern( regexp = "^(?:[A-Za-z&Ññ]{3}|[A-Za-z&Ññ]{4})\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[A-Za-z0-9]{3}$", message = "El tax_id debe seguir el formato oficial")
    @Column(name ="tax_id", unique = true, nullable = false, length = 13)
    private String taxId;
    @Column(name ="create_at", nullable = false)
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();
    @PrePersist
    public void onCreate(){
        ZoneId zoneMadagascar = ZoneId.of("Indian/Antananarivo");
        this.createdAt = ZonedDateTime.now(zoneMadagascar).toLocalDateTime();
    }
}
