package com.stone.springbootrestblog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.scheduling.concurrent.DefaultManagedTaskExecutor;

import java.util.Date;
@AllArgsConstructor
@Getter
public class ErrorDetails {

    private Date date;
    private String message;
    private String details;

}
