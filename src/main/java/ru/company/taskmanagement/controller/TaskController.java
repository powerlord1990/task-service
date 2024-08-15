package ru.company.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.company.taskmanagement.common.dto.TaskDTO;
import ru.company.taskmanagement.common.dto.TaskUpdateDTO;
import ru.company.taskmanagement.entity.Task;
import ru.company.taskmanagement.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    @Operation(summary = "Process a register operation", description = "Processes a register operation based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation processed successfully"),
            @ApiResponse(responseCode = "404", description = "Tasks not found")
    })
    public ResponseEntity<List<Task>> getTask(@PathVariable long id) {
        List<Task> tasks = taskService.getTasksByAuthor(id);
        if (tasks != null) {
            return ResponseEntity.ok(tasks);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Process a register operation", description = "Processes a register operation based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation processed successfully"),
            @ApiResponse(responseCode = "404", description = "User with this username not exist")
    })
    public ResponseEntity<Task> createTack(@RequestBody TaskDTO task) {
        Task createdTask = taskService.createTask(task);
        return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Process a register operation", description = "Processes a register operation based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation processed successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found or assigned user not found")
    })
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskUpdateDTO taskDto) {
        Task updatedTask = taskService.updateTask(id, taskDto);
        if (updatedTask != null) {
            return ResponseEntity.ok(updatedTask);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Process a register operation", description = "Processes a register operation based on the provided request")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation processed successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();

    }
}
