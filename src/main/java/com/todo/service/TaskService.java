package com.todo.service;

import com.todo.dao.TaskDAO;
import com.todo.domain.Status;
import com.todo.domain.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class TaskService {
    private final TaskDAO taskDAO;

    public TaskService(TaskDAO taskDAO) {
        this.taskDAO = taskDAO;
    }

    public List<Task> getAllTasks(int offset, int limit) {
        return taskDAO.getAll(offset, limit);
    }

    public int getAllCount() {
        return taskDAO.getAllCount();
    }

    @Transactional
    public void edit(int id, String description, Status status) {
        Task task = taskDAO.getById(id);
        if (isNull(task)) {
            throw new RuntimeException("Not found task");
        }

        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
    }

    public Task create(String description, Status status) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(status);
        taskDAO.saveOrUpdate(task);
        return task;
    }

    @Transactional
    public void delete(int id) {
        Task task = taskDAO.getById(id);
        if (task == null) {
            throw new RuntimeException("Not found task");
        }

        taskDAO.delete(task);

    }
}