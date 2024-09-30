package com.todo.service;

import com.todo.dao.TaskDAO;
import com.todo.domain.Status;
import com.todo.domain.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    private TaskDAO taskDAO;
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskDAO = Mockito.mock(TaskDAO.class);
        taskService = new TaskService(taskDAO);
    }

    @Test
    public void getAllTasks() {
        int offset = 0;
        int limit = 10;
        Task task = new Task();
        task.setDescription("Test task");
        task.setStatus(Status.IN_PROGRESS);
        when(taskDAO.getAll(offset, limit)).thenReturn(Collections.singletonList(task));

        List<Task> tasks = taskService.getAllTasks(offset, limit);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertEquals("Test task", tasks.get(0).getDescription());
    }

    @Test
    public void getAllCount() {
        when(taskDAO.getAllCount()).thenReturn(5);

        int count = taskService.getAllCount();

        assertEquals(5, count);
    }

    @Test
    public void edit() {
        int id = 1;
        String newDescription = "Updated task";
        Status newStatus = Status.DONE;
        Task task = new Task();
        task.setDescription("Old task");
        task.setStatus(Status.IN_PROGRESS);
        when(taskDAO.getById(id)).thenReturn(task);

        taskService.edit(id, newDescription, newStatus);

        assertEquals(newDescription, task.getDescription());
        assertEquals(newStatus, task.getStatus());
        verify(taskDAO, times(1)).saveOrUpdate(task);
    }

    @Test
    public void createTask() {
        Task createdTask = new Task();
        createdTask.setDescription("Test task");
        createdTask.setStatus(Status.IN_PROGRESS);

        doNothing().when(taskDAO).saveOrUpdate(any(Task.class));

        Task result = taskService.create("Test task", Status.IN_PROGRESS);

        assertEquals("Test task", result.getDescription());
        assertEquals(Status.IN_PROGRESS, result.getStatus());
    }

    @Test
    public void delete() {
        int id = 1;
        Task task = new Task();
        when(taskDAO.getById(id)).thenReturn(task);

        taskService.delete(id);

        verify(taskDAO, times(1)).delete(task);
    }

    @Test
    public void deleteNotFound() {
        int id = 2;
        when(taskDAO.getById(id)).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            taskService.delete(id);
        });
        assertEquals("Not found task", exception.getMessage());
    }
}