package ru.company.taskservice.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.company.taskservice.common.dto.TaskDTO;
import ru.company.taskservice.controller.TaskController;
import ru.company.taskservice.service.TaskService;


import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    public TaskControllerTest() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void testGetTasks() throws Exception {
        when(taskService.getTasksByAuthor(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/tasks/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreateTask() throws Exception {
        TaskDTO taskDTO = new TaskDTO();
        // заполните taskDTO необходимыми данными

        when(taskService.createTask(taskDTO)).thenReturn(null);

        mockMvc.perform(post("/api/v1/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"testTask\", \"description\":\"taskDescription\"}"))
                .andExpect(status().is(HttpStatus.CREATED.value()));
    }
}