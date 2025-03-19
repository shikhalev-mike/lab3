package com.example.lab3.service;

import com.example.lab3.dto.TaskDTO;
import com.example.lab3.entity.Project;
import com.example.lab3.entity.Task;
import com.example.lab3.mapper.TaskMapper;
import com.example.lab3.repository.ProjectRepository;
import com.example.lab3.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper, ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<TaskDTO> findAllByProjectId(Long projectId) {
        return taskMapper.toTaskDTO(taskRepository.findByProject_Id(projectId));
    }

    @Override
    public Optional<TaskDTO> findByIdAndProjectId(Long id, Long projectId) {
        return taskRepository.findByIdAndProject_Id(id, projectId).map(taskMapper::toTaskDTO);
    }

    @Override
    public TaskDTO create(Long projectId, TaskDTO taskDTO) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            Task task = taskMapper.toEntity(taskDTO);
            task.setProject(project.get());
            return taskMapper.toTaskDTO(taskRepository.save(task));
        }
        return null;
    }

    @Override
    public Optional<TaskDTO> update(Long id, TaskDTO taskDTO) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent()) {
            Task existingTask = task.get();

            existingTask.setName(taskDTO.name());
            existingTask.setDescription(taskDTO.description());
            existingTask.setEndDate(taskDTO.endDate());
            existingTask.setCompleted(taskDTO.completed());

            return Optional.of(taskMapper.toTaskDTO(taskRepository.save(existingTask)));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id, Long projectId) {
        if (projectRepository.existsById(projectId)) {
            taskRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void deleteCompleted(Long projectId) {
        projectRepository.findById(projectId).ifPresent(taskRepository::deleteByCompletedTrueAndProject);
    }
}
