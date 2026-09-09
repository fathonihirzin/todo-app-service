package com.todo.app.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PageDataResponse<T> {
    int page;
    int pageSize;
    Integer total;
    List<T> list;
}
