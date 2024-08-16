package ru.company.taskservice.mapper;

import org.mapstruct.Mapper;
import ru.company.taskservice.common.dto.TaskDTO;
import ru.company.taskservice.common.dto.TaskUpdateDTO;
import ru.company.taskservice.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO toDto(Task task);
    Task toEntity(TaskDTO taskDTO);
    Task  toEntity(TaskUpdateDTO taskUpdateDTO);

    Task toEntity(Task updatedTask);
}
