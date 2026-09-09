package com.savarjishodga.dgataskebismartva.service;

import com.savarjishodga.dgataskebismartva.dto.TaskResponseDTO;
import com.savarjishodga.dgataskebismartva.dto.UserRequestDTO;
import com.savarjishodga.dgataskebismartva.dto.UserResponseDTO;
import com.savarjishodga.dgataskebismartva.entity.Task;
import com.savarjishodga.dgataskebismartva.entity.User;
import com.savarjishodga.dgataskebismartva.exception.ResourceNotFoundException;
import com.savarjishodga.dgataskebismartva.repository.TaskRepository;
import com.savarjishodga.dgataskebismartva.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO){
        User user = mapToEntity(userRequestDTO);
        User savedUser =userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id not found : " + id));

        existingUser.setFirstName(userRequestDTO.getFirstName());
        existingUser.setLastName(userRequestDTO.getLastName());
        existingUser.setEmail(userRequestDTO.getEmail());

        User updatedUser = userRepository.save(existingUser);
        return mapToResponse(updatedUser);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with this id not found : " + id));
        return mapToResponse(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers(){
        return  userRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public void deleteUser(Long id){
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User with this id not found : " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<TaskResponseDTO> getUserTasks(Long userId){
        if (!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User with this id not found : " + userId);
        }
        List<Task> tasks =taskRepository.findByAssigneeId(userId);
        return tasks.stream()
                .map(this::mapTaskToResponse)
                .toList();
    }
    
    private User mapToEntity(UserRequestDTO userRequestDTO){
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        return user;
    }

    private UserResponseDTO mapToResponse(User user){
        if (user == null){
            return null;
        }
        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setFirstName(user.getFirstName());
        userResponseDTO.setLastName(user.getLastName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setCreatedAt(user.getCreatedAt());
        return userResponseDTO;
    }

    private TaskResponseDTO mapTaskToResponse(Task task){
        if (task == null){
            return null;
        }
        TaskResponseDTO taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(task.getId());
        taskResponseDTO.setTitle(task.getTitle());
        taskResponseDTO.setDescription(task.getDescription());

        if(task.getStatus() != null){
            taskResponseDTO.setStatus(task.getStatus().getCode());
        }

        if (task.getAssignee() != null) {
            taskResponseDTO.setAssigneeName(task.getAssignee().getFirstName() + " " + task.getAssignee().getLastName());
        }

        if (task.getProject() != null) {
            taskResponseDTO.setProjectName(task.getProject().getName());
        }

        taskResponseDTO.setDueDate(task.getDueDate());
        taskResponseDTO.setCreatedAt(task.getCreatedAt());

        return taskResponseDTO;
    }
}
