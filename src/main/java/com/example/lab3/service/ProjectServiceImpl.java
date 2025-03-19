package com.example.lab3.service;

import com.example.lab3.dto.ProjectDTO;
import com.example.lab3.entity.Project;
import com.example.lab3.mapper.ProjectMapper;
import com.example.lab3.repository.ProjectRepository;
import com.example.lab3.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final TaskRepository taskRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper, TaskRepository taskRepository) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public List<ProjectDTO> findAll(String search) {
        List<Project> projects;
        if (search != null) {
            projects = projectRepository.findByNameContainsIgnoreCaseAndDescriptionContainsIgnoreCase(search, search);
        } else {
            projects = projectRepository.findAll();
        }

        return projectMapper.toProjectDTO(projects);
    }

    @Override
    public Optional<ProjectDTO> findById(Long id) {
        return projectRepository.findById(id).map(projectMapper::toProjectDTO);
    }

    @Override
    public ProjectDTO create(ProjectDTO projectDTO) {
        return projectMapper.toProjectDTO(projectRepository.save(projectMapper.toEntity(projectDTO)));
    }

    @Override
    public Optional<ProjectDTO> update(Long id, ProjectDTO projectDTO) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isPresent()) {
            Project existingProject = project.get();

            existingProject.setName(projectDTO.name());
            existingProject.setDescription(projectDTO.description());
            existingProject.setStartDate(projectDTO.startDate());
            existingProject.setEndDate(projectDTO.endDate());

            return Optional.of(projectMapper.toProjectDTO(projectRepository.save(existingProject)));
        }
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
        }
    }

    @Override
    public Map<Long, Long> countUncompletedTasks() {
        Map<Long, Long> countMap = new HashMap<>();
        taskRepository.findByCompletedFalse().forEach(task -> countMap.merge(task.getProject().getId(), 1L, (oldValue, newValue) -> oldValue + 1));
        return countMap;
    }
}
