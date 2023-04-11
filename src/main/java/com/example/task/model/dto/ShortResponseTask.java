package com.example.task.model.dto;

import com.example.task.model.Task;

import java.io.Serializable;

public class ShortResponseTask implements Serializable {
    private final Long id;
    private final String title;
    private final String status;

    public ShortResponseTask(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.status = task.getStatus();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }
}
