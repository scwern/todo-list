package com.todo.controller;
import com.todo.domain.Status;
import com.todo.domain.Task;
import com.todo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TaskControllerTest {

    private TaskService taskService;
    private TaskController taskController;

    @BeforeEach
    void setUp() {
        taskService = Mockito.mock(TaskService.class);
        taskController = new TaskController(taskService);
    }

    @Test
    void tasks() {
        Model model = Mockito.mock(Model.class);
        List<Task> mockTasks = Arrays.asList(new Task(), new Task());

        when(taskService.getAllTasks(0, 10)).thenReturn(mockTasks);
        when(taskService.getAllCount()).thenReturn(20);

        String viewName = taskController.tasks(model, 1, 10);

        verify(model).addAttribute("tasks", mockTasks);
        verify(model).addAttribute("current_page", 1);
        verify(model).addAttribute("page_numbers", Arrays.asList(1, 2));
        assertEquals("tasks", viewName);
    }

    @Test
    void edit() {
        Model model = Mockito.mock(Model.class);
        int taskId = 1;
        String description = "Updated task description";
        Status status = Status.IN_PROGRESS;

        String viewName = taskController.edit(model, taskId, description, status);

        verify(taskService).edit(taskId, description, status);
        assertEquals("tasks", viewName);
    }

    @Test
    void createTask() {
        Task mockTask = new Task();
        mockTask.setDescription("New task description");
        mockTask.setStatus(Status.IN_PROGRESS);

        when(taskService.create(mockTask.getDescription(), mockTask.getStatus())).thenReturn(mockTask);

        ResponseEntity<Task> response = taskController.createTask(mockTask);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(mockTask, response.getBody());
    }

    @Test
    void delete() {
        int taskId = 1;

        ResponseEntity<String> response = taskController.delete(taskId);

        verify(taskService).delete(taskId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Task deleted successfully", response.getBody());
    }
}