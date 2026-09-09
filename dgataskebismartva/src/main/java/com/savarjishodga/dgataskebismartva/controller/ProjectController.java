package com.savarjishodga.dgataskebismartva.controller;

import com.savarjishodga.dgataskebismartva.dto.ProjectRequestDTO;
import com.savarjishodga.dgataskebismartva.dto.ProjectResponseDTO;
import com.savarjishodga.dgataskebismartva.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@Tag(name = "Project Management", description = "Proeqtebis martvis danayofi")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Create a new project")
    public ResponseEntity<ProjectResponseDTO> createProject(@Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.createProject(projectRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Get all projects")
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get project by ID")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing project")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectRequestDTO projectRequestDTO) {
        return ResponseEntity.ok(projectService.updateProject(id, projectRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project by ID")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok("Project successfully deleted!");
    }

    @GetMapping("/count")
    @Operation(summary = "Get total count of all projects")
    public ResponseEntity<Long> getTotalProjectCount() {
        return ResponseEntity.ok(projectService.getTotalProjectCount());
    }

//    @GetMapping("/{id}/tasks/count")
//    @Operation(summary = "Get total count of tasks for a specific project")
//    public ResponseEntity<Long> getTaskCountByProjectId(@PathVariable Long id) {
//        return ResponseEntity.ok(projectService.getTaskCountByProjectId(id));
//    }
}
