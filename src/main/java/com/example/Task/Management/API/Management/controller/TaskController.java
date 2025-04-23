package com.example.Task.Management.API.Management.controller;

import com.example.Task.Management.API.Management.model.Task;
import com.example.Task.Management.API.Management.model.User;
import com.example.Task.Management.API.Management.repository.UserRepository;
import com.example.Task.Management.API.Management.security.JwtUtil;
import com.example.Task.Management.API.Management.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Task Management", description = "Endpoints for managing tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public Task createTask(@RequestParam @Parameter(description = "Task title") String title,
                           @RequestParam @Parameter(description = "Task description") String description,
                           @RequestHeader("Authorization") @Parameter(description = "JWT token (Bearer <token>)") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskService.createTask(title, description, user.getId());
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieves all tasks for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "List of tasks retrieved successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public List<Task> getTasks(@RequestHeader("Authorization") @Parameter(description = "JWT token (Bearer <token>)") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskService.getTasks(user.getId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Updates an existing task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or ID format"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Task or user not found")
    })
    public Task updateTask(@PathVariable @Parameter(description = "Task ID") Long id,
                           @RequestParam @Parameter(description = "Task title") String title,
                           @RequestParam @Parameter(description = "Task description") String description,
                           @RequestParam @Parameter(description = "Task completion status") boolean completed,
                           @RequestHeader("Authorization") @Parameter(description = "JWT token (Bearer <token>)") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskService.updateTask(id, title, description, completed, user.getId());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid ID format"),
            @ApiResponse(responseCode = "403", description = "Invalid or missing JWT token"),
            @ApiResponse(responseCode = "404", description = "Task or user not found")
    })
    public void deleteTask(@PathVariable @Parameter(description = "Task ID") Long id,
                           @RequestHeader("Authorization") @Parameter(description = "JWT token (Bearer <token>)") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        taskService.deleteTask(id, user.getId());
    }
}