package ru.company.taskservice.common.dto;

import lombok.Data;

@Data
public class TaskDTO {
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long authorId;
    private Long assigneeId;
}
