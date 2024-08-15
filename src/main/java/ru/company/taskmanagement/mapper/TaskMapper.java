package ru.company.taskmanagement.mapper;

import org.mapstruct.Mapper;
import ru.company.taskmanagement.common.dto.TaskDTO;
import ru.company.taskmanagement.common.dto.TaskUpdateDTO;
import ru.company.taskmanagement.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);
    Task toEntity(TaskDTO taskDTO);
    Task  toEntity(TaskUpdateDTO taskUpdateDTO);

    Task toEntity(Task updatedTask);
}
