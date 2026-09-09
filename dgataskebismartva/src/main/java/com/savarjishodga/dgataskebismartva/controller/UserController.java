package com.savarjishodga.dgataskebismartva.controller;

import com.savarjishodga.dgataskebismartva.dto.TaskResponseDTO;
import com.savarjishodga.dgataskebismartva.dto.UserRequestDTO;
import com.savarjishodga.dgataskebismartva.dto.UserResponseDTO;
import com.savarjishodga.dgataskebismartva.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "User Management", description = "User managementis samartavi danayofi")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create user")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(userRequestDTO));
    }

    @GetMapping
    @Operation(summary = "Get all users")
    public  ResponseEntity<List<UserResponseDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by ID")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted!");
    }

    @GetMapping("/{id}/tasks")
    @Operation(summary = "Get all tasks assigned to a specific user")
    public ResponseEntity<List<TaskResponseDTO>> getUserTasks(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserTasks(id));
    }

}
