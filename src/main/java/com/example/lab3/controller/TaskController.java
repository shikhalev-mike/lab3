package com.example.lab3.controller;

import com.example.lab3.dto.TaskDTO;
import com.example.lab3.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<TaskDTO> getTasksByProjectId(@PathVariable Long projectId) {
        return taskService.findAllByProjectId(projectId);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long projectId, @PathVariable Long taskId) {
        return taskService.findByIdAndProjectId(taskId, projectId).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@PathVariable Long projectId, @RequestBody TaskDTO taskDTO) {
        return new ResponseEntity<>(taskService.create(projectId, taskDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestBody TaskDTO taskDTO) {
        return taskService.update(projectId, taskDTO).map(ResponseEntity::ok).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable Long projectId, @PathVariable Long taskId) {
        taskService.delete(projectId, taskId);
    }

    @DeleteMapping("/completed")
    public void deleteCompletedTasks(@PathVariable Long projectId) {
        taskService.deleteCompleted(projectId);
    }
}
