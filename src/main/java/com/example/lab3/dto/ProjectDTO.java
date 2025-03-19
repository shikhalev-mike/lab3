package com.example.lab3.dto;

import java.time.LocalDate;

public record ProjectDTO(Long id, String name, String description, LocalDate startDate, LocalDate endDate) {
    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public LocalDate startDate() {
        return startDate;
    }

    public LocalDate endDate() {
        return endDate;
    }
}
