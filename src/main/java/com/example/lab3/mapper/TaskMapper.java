package com.example.lab3.mapper;

import com.example.lab3.dto.TaskDTO;
import com.example.lab3.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    Task toEntity(TaskDTO taskDTO);

    List<Task> toEntity(List<TaskDTO> taskDTO);

    TaskDTO toTaskDTO(Task task);

    List<TaskDTO> toTaskDTO(List<Task> task);
}
