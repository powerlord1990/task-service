package ru.company.taskmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.company.taskmanagement.common.dto.TaskDTO;
import ru.company.taskmanagement.common.dto.TaskUpdateDTO;
import ru.company.taskmanagement.entity.Task;
import ru.company.taskmanagement.entity.User;
import ru.company.taskmanagement.entity.enums.TaskPriority;
import ru.company.taskmanagement.entity.enums.TaskStatus;
import ru.company.taskmanagement.exception.TaskNotFoundException;
import ru.company.taskmanagement.exception.UserNotFoundException;
import ru.company.taskmanagement.mapper.TaskMapper;
import ru.company.taskmanagement.repository.TaskRepository;
import ru.company.taskmanagement.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;


    public Task createTask(TaskDTO taskDTO){
        User author = userRepository.findById(taskDTO.getAuthorId())
                .orElseThrow(()-> new UserNotFoundException("Author not found with id: " + taskDTO.getAuthorId()));
        Task task = taskMapper.toEntity(taskDTO);
        task.setAuthor(author);
        return taskRepository.save(task);
    }

    public List<Task> getTasksByAuthor(Long authorId) {
        Optional<List<Task>> tasksByAuthorId = taskRepository.findByAuthorId(authorId);
        return tasksByAuthorId.orElseThrow(()-> new TaskNotFoundException("Tasks not found"));
    }

    public boolean deleteTask(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task with id: " + taskId + " not found"));
        taskRepository.delete(task);
        return true;
    }

    @Transactional
    public Task updateTask(Long taskId, TaskUpdateDTO taskUpdateDTO) {
        // Получаем текущую задачу из базы данных
        Task existingTask = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(String.format("Task with id: %d not found", taskId)));

        // Обновляем поля только если они присутствуют в DTO
        if (taskUpdateDTO.getTitle() != null) {
            existingTask.setTitle(taskUpdateDTO.getTitle());
        }
        if (taskUpdateDTO.getDescription() != null) {
            existingTask.setDescription(taskUpdateDTO.getDescription());
        }
        if (taskUpdateDTO.getStatus() != null) {
            existingTask.setStatus(TaskStatus.valueOf(taskUpdateDTO.getStatus()));
        }
        if (taskUpdateDTO.getPriority() != null) {
            existingTask.setPriority(TaskPriority.valueOf(taskUpdateDTO.getPriority()));
        }
        if (taskUpdateDTO.getAssigneeId() != null) {
            User assignee = userRepository.findById(taskUpdateDTO.getAssigneeId())
                    .orElseThrow(() -> new UserNotFoundException("Assignee not found"));
            existingTask.setAssignee(assignee);
        }

        // Сохраняем обновлённую задачу
        return taskRepository.save(existingTask);
    }
}
