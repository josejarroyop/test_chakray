package com.example.demo.dto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserDTO {

    private UUID uuid;
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Phone is required")
    private String phone;
    @NotBlank(message = "Password is required")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @NotBlank(message = "tax_id is required")
    @Pattern(
        regexp = "^(?:[A-Za-z&Ññ]{3}|[A-Za-z&Ññ]{4})\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[A-Za-z0-9]{3}$",
        message = "tax_id invalid format"
    )
    private String taxId;
    private LocalDateTime createdAt;
    private List<AddressDTO> addresses ;
}
