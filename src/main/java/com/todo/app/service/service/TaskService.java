package com.todo.app.service.service;

import com.todo.app.service.model.dto.TaskDTO;
import com.todo.app.service.model.dto.TaskParams;
import com.todo.app.service.model.Task;
import com.todo.app.service.model.response.DataResponse;
import com.todo.app.service.model.response.DatatableResponse;
import com.todo.app.service.model.response.DefaultResponse;

public interface TaskService {
    DefaultResponse addTasks(TaskDTO task);

    DefaultResponse markedDoneTask(String id);

    DefaultResponse editTask(String id, TaskDTO task);

    DatatableResponse<Task> getAllTask(TaskParams task);

    DataResponse<Task> getTask(String id);

    DefaultResponse deleteTask(String id);
}
