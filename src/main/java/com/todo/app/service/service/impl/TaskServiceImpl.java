package com.todo.app.service.service.impl;

import com.todo.app.service.exception.custom.InternalServerErrorException;
import com.todo.app.service.exception.custom.ResourceNotFoundException;
import com.todo.app.service.mapper.TaskMapper;
import com.todo.app.service.model.dto.TaskDTO;
import com.todo.app.service.model.dto.TaskParams;
import com.todo.app.service.model.Task;
import com.todo.app.service.model.response.*;
import com.todo.app.service.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;

    @Override
    public DefaultResponse addTasks(TaskDTO taskDTO) {

        try {
            Task task = new Task();

            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setStatus(taskDTO.getStatus());
            task.setCreatedAt(LocalDateTime.now());
            task.setCreatedBy("SYSTEM");

            taskMapper.addTask(task);

            return new DefaultResponse(ResponseMessage.SUCCESS, "Task successfully added!", HttpStatus.CREATED.value());
        } catch (Exception e) {
            log.error("Error adding task: title={} ", taskDTO.getTitle(), e);
            throw new InternalServerErrorException("Error adding task");
        }
    }

    @Override
    public DefaultResponse markedDoneTask(String id) {

        try {
            Task task = taskMapper.findTask(id);

            if (task == null) {
                throw new ResourceNotFoundException("Task not found with id: " + id);
            }

            taskMapper.updateStatusTask(id, LocalDateTime.now());

            log.info("Task {} status updated to {}", id, "DONE");
            return new DefaultResponse(ResponseMessage.SUCCESS, "Task status updated successfully", HttpStatus.OK.value());

        } catch (ResourceNotFoundException e) {
            throw e;

        } catch (Exception e) {
            log.error("Error when updating status task: {}", e.getMessage());
            throw new InternalServerErrorException("Failed to update task status");
        }
    }

    @Override
    public DefaultResponse editTask(String id, TaskDTO task) {

        try {

            Task isExisting = taskMapper.findTask(id);

            if (isExisting == null) {
                throw new ResourceNotFoundException("Task not found with id: " + id);
            }

            taskMapper.updateTask(id, LocalDateTime.now(), task);

            return new DefaultResponse(ResponseMessage.SUCCESS, "Task successfully edited!", HttpStatus.OK.value());

        } catch (ResourceNotFoundException e) {
            throw e;

        } catch (Exception e) {
            log.error("Error when editing the task: {}", e.getMessage());
            throw new InternalServerErrorException("Error when editing the task!");
        }
    }

    @Override
    public DatatableResponse<Task> getAllTask(TaskParams task) {

        try {
            List<Task> pageResult = taskMapper.findAllTask(task);
            int total = taskMapper.countAllTask(task);

            PageDataResponse<Task> data = new PageDataResponse<>(task.getPage(), task.getPageSize(), total, pageResult);

            return new DatatableResponse<>(ResponseMessage.SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), data);
        } catch (Exception e) {
            log.error("Error when searching for task: {}", e.getMessage());
            throw new InternalServerErrorException("Error when searching for task!");
        }
    }

    @Override
    public DataResponse<Task> getTask(String id) {

        try {

            Task task = taskMapper.findTask(id);

            if (task == null) {
                throw new ResourceNotFoundException("Task not found with id: " + id);
            }

            return new DataResponse<>(ResponseMessage.SUCCESS, ResponseMessage.DATA_FETCHED, HttpStatus.OK.value(), task);
        } catch (ResourceNotFoundException e) {
            throw e;

        } catch (Exception e) {
            log.error("Error when getting the task: {}", e.getMessage());
            throw new InternalServerErrorException("Error when getting the task!");
        }
    }

    @Override
    public DefaultResponse deleteTask(String id) {

        try {
            Task task = taskMapper.findTask(id);

            if (task == null) {
                throw new ResourceNotFoundException("Task not found with id: " + id);
            }

            taskMapper.deleteTask(id);

            log.info("Task {} deleted", id);
            return new DefaultResponse(ResponseMessage.SUCCESS, "Task deleted successfully", HttpStatus.OK.value());

        } catch (ResourceNotFoundException e) {
            throw e;

        } catch (Exception e) {
            log.error("Error when deleting task: {}", e.getMessage());
            throw new InternalServerErrorException("Failed to delete task");
        }
    }
}
