package com.todo.app.service.controller;

import com.todo.app.service.model.dto.TaskDTO;
import com.todo.app.service.model.dto.TaskParams;
import com.todo.app.service.model.Task;
import com.todo.app.service.model.response.DataResponse;
import com.todo.app.service.model.response.DatatableResponse;
import com.todo.app.service.model.response.DefaultResponse;
import com.todo.app.service.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/create")
    @Operation(
            summary = "Create new task/tasks",
            description = "Create new task/tasks and save it into the data source"
    )
    public ResponseEntity<DefaultResponse> addTasks(@Valid @RequestBody TaskDTO task){
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addTasks(task));
    }

    @PutMapping("/mark-done/{id}")
    @Operation(
            summary = "To mark done a task",
            description = "To mark done a task and save it into the data source"
    )
    public ResponseEntity<DefaultResponse> markedDoneTask(@PathVariable String id){
        return ResponseEntity.ok(taskService.markedDoneTask(id));
    }

    @PutMapping("/edit/{id}")
    @Operation(
            summary = "To edit a task",
            description = "To edit e a task and save it into the data source"
    )
    public ResponseEntity<DefaultResponse> editTask(@PathVariable String id, @RequestBody TaskDTO task){
        return ResponseEntity.ok(taskService.editTask(id, task));
    }

    @GetMapping("/get-all")
    @Operation(
            summary = "To get all task",
            description = "To get all task from data source with pagination and filter"
    )
    public ResponseEntity<DatatableResponse<Task>> getAllTask(@ModelAttribute TaskParams task){
        return ResponseEntity.ok(taskService.getAllTask(task));
    }

    @PostMapping("/get-all")
    @Operation(
            summary = "To get all task v2",
            description = "To get all task from data source with pagination and filter if there's sensitive information "
    )
    public ResponseEntity<DatatableResponse<Task>> getAllTaskV2(@RequestBody TaskParams task){
        return ResponseEntity.ok(taskService.getAllTask(task));
    }

    @GetMapping("/get/{id}")
    @Operation(
            summary = "To get a task",
            description = "To get a task from data source"
    )
    public ResponseEntity<DataResponse<Task>> getAllTask(@PathVariable String id){
        return ResponseEntity.ok(taskService.getTask(id));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(
            summary = "To delete a task",
            description = "To delete a task and remove it from the data source"
    )
    public ResponseEntity<DefaultResponse> deleteTask(@PathVariable String id){
        return ResponseEntity.ok(taskService.deleteTask(id));
    }
}
