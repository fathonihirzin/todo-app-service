package com.todo.app.service.mapper;

import com.todo.app.service.model.dto.TaskDTO;
import com.todo.app.service.model.dto.TaskParams;
import com.todo.app.service.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface TaskMapper {
    void addTask(Task task);

    Task findTask(@Param("id") String id);

    void updateStatusTask(@Param("id") String id, @Param("timeNow") LocalDateTime timeNow);

    void updateTask(@Param("id") String id, @Param("timeNow") LocalDateTime timeNow, @Param("task") TaskDTO task);

    List<Task> findAllTask(TaskParams task);

    int countAllTask(TaskParams task);

    void deleteTask(String id);
}
