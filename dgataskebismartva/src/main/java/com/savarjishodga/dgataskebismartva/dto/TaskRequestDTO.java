package com.savarjishodga.dgataskebismartva.dto;

import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskRequestDTO {

    @NotBlank(message = "Title is required")
    private  String title;

    private String description;

    @NotNull(message = "Status is required")
    private TaskStatusEnum status;

    private Long assigneeId;

    @NotNull(message = "Project Id is required")
    private Long projectId;

    private LocalDateTime dueDate;
}
