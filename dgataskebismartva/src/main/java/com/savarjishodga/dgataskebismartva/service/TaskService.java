package com.savarjishodga.dgataskebismartva.service;
import com.savarjishodga.dgataskebismartva.dto.TaskRequestDTO;
import com.savarjishodga.dgataskebismartva.dto.TaskResponseDTO;
import com.savarjishodga.dgataskebismartva.entity.*;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import com.savarjishodga.dgataskebismartva.exception.ResourceNotFoundException;
import com.savarjishodga.dgataskebismartva.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final TaskStatusRepository taskStatusRepository;

    @Transactional
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO){
        Project project = projectRepository.findById(taskRequestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project with this id not found: " + taskRequestDTO.getProjectId()));

        TaskStatus status = taskStatusRepository.findByCode(taskRequestDTO.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + taskRequestDTO.getStatus()));

        User assignee = null;
        if (taskRequestDTO.getAssigneeId() != null){
            assignee = userRepository.findById(taskRequestDTO.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with this id not found: " + taskRequestDTO.getAssigneeId()));
        }
        Task task = mapToEntity(taskRequestDTO, status, project, assignee);
        Task savedTask = taskRepository.save(task);
        return mapToResponse(savedTask);
    }

    @Transactional
    public TaskResponseDTO updateTask(Long id, TaskRequestDTO taskRequestDTO) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with this id not found: " + id));

        Project project = projectRepository.findById(taskRequestDTO.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project with this id not found: " + taskRequestDTO.getProjectId()));

        TaskStatus status = taskStatusRepository.findByCode(taskRequestDTO.getStatus())
                .orElseThrow(() -> new ResourceNotFoundException("Status not found: " + taskRequestDTO.getStatus()));

        User assignee = null;
        if (taskRequestDTO.getAssigneeId() != null) {
            assignee = userRepository.findById(taskRequestDTO.getAssigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User with this id not found: " + taskRequestDTO.getAssigneeId()));
        }

        existingTask.setTitle(taskRequestDTO.getTitle());
        existingTask.setDescription(taskRequestDTO.getDescription());
        existingTask.setStatus(status);
        existingTask.setProject(project);
        existingTask.setAssignee(assignee);
        existingTask.setDueDate(taskRequestDTO.getDueDate());

        Task updatedTask = taskRepository.save(existingTask);
        return mapToResponse(updatedTask);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getAllTasks(TaskStatusEnum status) {
        List<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findByStatusCode(status);
        } else {
            tasks = taskRepository.findAll();
        }
        return tasks.stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponseDTO getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task with this id not found: " + id));
        return mapToResponse(task);
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task with this id not found: " + id);
        }
        taskRepository.deleteById(id);
    }

    private Task mapToEntity(TaskRequestDTO taskRequestDTO, TaskStatus taskStatus, Project project, User assignee){
        Task task = new Task();
        task.setTitle(taskRequestDTO.getTitle());
        task.setDescription(taskRequestDTO.getDescription());
        task.setStatus(taskStatus);
        task.setProject(project);
        task.setAssignee(assignee);
        task.setDueDate(taskRequestDTO.getDueDate());
        return task;
    }

    private TaskResponseDTO mapToResponse(Task task){
        if (task == null){
            return null;
        }

        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setDescription(task.getDescription());

        if (task.getStatus() != null){
            taskResponseDTO.setStatus(task.getStatus().getCode());
        }

        if (task.getAssignee() != null){
            taskResponseDTO.setAssigneeName(task.getAssignee().getFirstName() + " " + task.getAssignee().getLastName());
        }

        if(task.getProject() != null){
            taskResponseDTO.setProjectName(task.getProject().getName());
        }

        taskResponseDTO.setDueDate(task.getDueDate());
        taskResponseDTO.setCreatedAt(task.getCreatedAt());

        return taskResponseDTO;
    }
}
