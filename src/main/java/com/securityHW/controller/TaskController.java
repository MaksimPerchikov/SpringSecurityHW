package com.securityHW.controller;

import com.securityHW.model.Task;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {


    private static final List<Task> TASKS = Arrays.asList(
            new Task(1L,"Create app","Need new application"),
            new Task(2L,"Update properties","Update properties of db in dev stand")
    );
    @PreAuthorize("hasAuthority('EMPLOYEE','TRAINEE')")
    @GetMapping("{id}")
    public Task getTask(@PathVariable("id") Long taskId){
        return TASKS.stream()
                .filter(task -> taskId.equals(task.getId()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Task "+ taskId + "not found"
                ));
    }
    @PreAuthorize("hasAuthority('task:write')")
    @PutMapping("{id}")
    public void updateTask(@PathVariable("id") Long taskId){
        System.out.println("Task updated");
    }

}


