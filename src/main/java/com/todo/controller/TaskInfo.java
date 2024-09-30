package com.todo.controller;

import com.todo.domain.Status;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskInfo {

    private String description;
    private Status status;
    
}
