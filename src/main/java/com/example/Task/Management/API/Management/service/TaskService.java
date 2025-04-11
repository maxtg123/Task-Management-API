package com.example.Task.Management.API.Management.service;

import com.example.Task.Management.API.Management.model.Task;
import com.example.Task.Management.API.Management.model.User;
import com.example.Task.Management.API.Management.repository.TaskRepository;
import com.example.Task.Management.API.Management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;

    public Task createTask(String title, String description, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setUser(user);
        return taskRepository.save(task);
    }

    public List<Task> getTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }

    public Task updateTask(Long taskId, String title, String description, boolean completed, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (!task.getUser().getId().equals(userId)) throw new RuntimeException("Unauthorized");
        task.setTitle(title);
        task.setDescription(description);
        task.setCompleted(completed);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId, Long userId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        if (!task.getUser().getId().equals(userId)) throw new RuntimeException("Unauthorized");
        taskRepository.delete(task);
    }
}