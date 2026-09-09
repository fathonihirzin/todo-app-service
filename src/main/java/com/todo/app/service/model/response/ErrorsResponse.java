package com.todo.app.service.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ErrorsResponse {
    String result ;
    String detail ;
    int code ;
    List<String> errors;
}
