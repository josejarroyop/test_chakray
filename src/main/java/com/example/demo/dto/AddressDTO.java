package com.example.demo.dto;

import lombok.Data;

@Data
public class AddressDTO {
    private Long id;
    private String name;
    private String street;
    private String countryCode;
}
