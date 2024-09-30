package com.todo.controller;

import com.todo.domain.Status;
import com.todo.domain.Task;
import com.todo.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.isNull;

@Controller
@RequestMapping("/")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String tasks(Model model,
                        @RequestParam(value = "page", required = false,defaultValue = "1") int page,
                        @RequestParam(value = "limit", required = false,defaultValue = "10") int limit) {

        List<Task> tasks = taskService.getAllTasks((page - 1) * limit, limit);
        model.addAttribute("tasks", tasks);
        model.addAttribute("current_page", page);
        int totalPages = (int)Math.ceil(1.0 * taskService.getAllCount() / limit);
        if(totalPages > 1) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("page_numbers", pageNumbers);
        }
        return "tasks";
    }

    @PostMapping("/{id}")
    public String edit(Model model,
                       @PathVariable Integer id,
                       @RequestParam String description,
                       @RequestParam Status status) {

        if (isNull(id) || id <= 0) {
            throw new RuntimeException("Invalid id");
        }

        taskService.edit(id, description, status);
        return tasks(model, 1, 10);

    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        try {
            Task createdTask = taskService.create(task.getDescription(), task.getStatus());
            return new ResponseEntity<>(createdTask, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        if (isNull(id) || id <= 0) {
            return ResponseEntity.badRequest().body("Invalid id");
        }
        taskService.delete(id);
        return ResponseEntity.ok("Task deleted successfully");
    }
}
