package com.savarjishodga.dgataskebismartva.controller;

import com.savarjishodga.dgataskebismartva.dto.TaskRequestDTO;
import com.savarjishodga.dgataskebismartva.dto.TaskResponseDTO;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import com.savarjishodga.dgataskebismartva.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Tag(name = "Task Management", description = "Taskebis martvis qvedanayofi")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Create a new task")
    public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(taskRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Get all tasks (shegvidzlia avirchiot statusis mixedvit)")
    public ResponseEntity<List<TaskResponseDTO>> getAllTasks(@RequestParam(required = false) TaskStatusEnum status) {
        return ResponseEntity.ok(taskService.getAllTasks(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing task")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task by ID")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok("Task successfully deleted!");
    }

}
