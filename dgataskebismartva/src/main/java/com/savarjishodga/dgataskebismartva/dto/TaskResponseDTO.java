package com.savarjishodga.dgataskebismartva.dto;

import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponseDTO {
    private  Long id;
    private String title;
    private String description;
    private TaskStatusEnum status;
    private String assigneeName;
    private String projectName;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;
}
