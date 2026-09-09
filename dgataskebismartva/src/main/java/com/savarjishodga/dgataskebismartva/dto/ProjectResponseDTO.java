package com.savarjishodga.dgataskebismartva.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProjectResponseDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
