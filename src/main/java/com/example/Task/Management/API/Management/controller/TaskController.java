package com.example.Task.Management.API.Management.controller;

import com.example.Task.Management.API.Management.model.Task;
import com.example.Task.Management.API.Management.model.User;
import com.example.Task.Management.API.Management.repository.UserRepository;
import com.example.Task.Management.API.Management.security.JwtUtil;
import com.example.Task.Management.API.Management.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public Task createTask(@RequestParam String title, @RequestParam String description, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskService.createTask(title, description, user.getId());
    }

    @GetMapping
    public List<Task> getTasks(@RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskService.getTasks(user.getId());
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestParam String title, @RequestParam String description, @RequestParam boolean completed, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        return taskService.updateTask(id, title, description, completed, user.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        String username = jwtUtil.extractUsername(token.replace("Bearer ", ""));
        User user = userRepository.findByUsername(username).orElseThrow();
        taskService.deleteTask(id, user.getId());
    }
}