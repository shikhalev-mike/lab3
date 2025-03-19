package com.example.lab3.repository;

import com.example.lab3.entity.Project;
import com.example.lab3.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCompletedFalse();

    List<Task> findByProject_Id(Long id);

    Optional<Task> findByIdAndProject_Id(Long id, Long projectId);

    void deleteByCompletedTrueAndProject(Project project);
}
