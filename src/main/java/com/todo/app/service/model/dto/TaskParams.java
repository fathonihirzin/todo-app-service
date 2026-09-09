package com.todo.app.service.model.dto;

import lombok.Data;

@Data
public class TaskParams {
    //pagination
    private int page;
    private int pageSize;
    private String orderBy = "created_at";
    private String sortOrder = "asc";

    //filter
    private String title;
    private String description;
    private String status;
}
