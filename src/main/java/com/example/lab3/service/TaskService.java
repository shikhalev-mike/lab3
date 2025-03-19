package com.example.lab3.service;

import com.example.lab3.dto.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<TaskDTO> findAllByProjectId(Long projectId);

    Optional<TaskDTO> findByIdAndProjectId(Long id, Long projectId);

    TaskDTO create(Long projectId, TaskDTO taskDTO);

    Optional<TaskDTO> update(Long id, TaskDTO taskDTO);

    void delete(Long id, Long projectId);

    void deleteCompleted(Long projectId);
}
