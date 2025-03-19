package com.example.lab3.service;

import com.example.lab3.dto.ProjectDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProjectService {
    List<ProjectDTO> findAll(String search);

    Optional<ProjectDTO> findById(Long id);

    ProjectDTO create(ProjectDTO projectDTO);

    Optional<ProjectDTO> update(Long id, ProjectDTO projectDTO);

    void delete(Long id);

    Map<Long, Long> countUncompletedTasks();
}
