package com.savarjishodga.dgataskebismartva.service;

import com.savarjishodga.dgataskebismartva.dto.ProjectRequestDTO;
import com.savarjishodga.dgataskebismartva.dto.ProjectResponseDTO;
import com.savarjishodga.dgataskebismartva.dto.TaskResponseDTO;
import com.savarjishodga.dgataskebismartva.entity.Project;
import com.savarjishodga.dgataskebismartva.exception.ResourceNotFoundException;
import com.savarjishodga.dgataskebismartva.repository.ProjectRepository;
import com.savarjishodga.dgataskebismartva.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public ProjectResponseDTO createProject(ProjectRequestDTO projectRequestDTO) {
        Project project = mapToEntity(projectRequestDTO);
        Project savedProject = projectRepository.save(project);
        return mapToResponse(savedProject);
    }

    @Transactional
    public ProjectResponseDTO updateProject(Long id, ProjectRequestDTO projectRequestDTO) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with this id not found: " + id));

        existingProject.setName(projectRequestDTO.getName());
        existingProject.setDescription(projectRequestDTO.getDescription());

        Project updatedProject = projectRepository.save(existingProject);
        return mapToResponse(updatedProject);
    }


    @Transactional(readOnly = true)
    public ProjectResponseDTO getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Project with this id not found: " + id));
        return mapToResponse(project);
    }


    @Transactional(readOnly = true)
    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    @Transactional
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Project with this id not found: " + id);
        }
        projectRepository.deleteById(id);
    }


//    @Transactional(readOnly = true)
//    public long getTaskCountByProjectId(Long projectId) {
//        if (!projectRepository.existsById(projectId)) {
//            throw new ResourceNotFoundException("Project with this id not found: " + projectId);
//        }
//        return taskRepository.countByProjectId(projectId);
//    }

    @Transactional(readOnly = true)
    public long getTotalProjectCount() {
        return projectRepository.count();
    }


    private Project mapToEntity(ProjectRequestDTO projectRequestDTO){
        Project project = new Project();
        project.setName(projectRequestDTO.getName());
        project.setDescription(projectRequestDTO.getDescription());
        return project;
    }

    private  ProjectResponseDTO mapToResponse(Project project){
        if (project == null){
            return null;
        }
        ProjectResponseDTO projectResponseDTO = new ProjectResponseDTO();
        projectResponseDTO.setId(project.getId());
        projectResponseDTO.setName(project.getName());
        projectResponseDTO.setDescription(project.getDescription());
        projectResponseDTO.setCreatedAt(project.getCreatedAt());
        return projectResponseDTO;
    }
}
