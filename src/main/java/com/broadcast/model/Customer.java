package com.broadcast.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Customer {
    private Long id;
    private String name;
    private LocalDate dateOfBirth;
    private String phoneNumber;
}
