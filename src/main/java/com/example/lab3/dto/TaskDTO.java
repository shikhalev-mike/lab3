package com.example.lab3.dto;

import java.time.LocalDate;

public record TaskDTO(Long id, String name, String description, LocalDate endDate, Boolean completed) {
    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public LocalDate endDate() {
        return endDate;
    }

    public Boolean completed() {
        return completed;
    }
}
