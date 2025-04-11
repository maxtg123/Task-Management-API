package com.example.Task.Management.API.Management.repository;

import com.example.Task.Management.API.Management.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
}