package com.example.lab3.mapper;

import com.example.lab3.dto.ProjectDTO;
import com.example.lab3.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {
    Project toEntity(ProjectDTO projectDTO);

    List<Project> toEntity(List<ProjectDTO> projectDTO);

    ProjectDTO toProjectDTO(Project project);

    List<ProjectDTO> toProjectDTO(List<Project> project);
}
