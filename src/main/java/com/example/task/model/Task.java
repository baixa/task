package com.example.task.model;

import com.example.task.model.dto.CreatedTask;
import com.example.task.model.dto.EditedTask;

public class Task {
    private Long id;
    private String title;
    private String description;
    private Long time;
    private String status;
    private Worker performer;

    public Task() {
    }

    public Task(EditedTask editedTask) {
        this.title = editedTask.getTitle();
        this.time = editedTask.getTime();
        this.status = editedTask.getStatus();
        this.description = editedTask.getDescription();
    }

    public Task(CreatedTask createdTask) {
        this.title = createdTask.getTitle();
        this.time = createdTask.getTime();
        this.status = createdTask.getStatus();
        this.description = createdTask.getDescription();
        this.performer = createdTask.getPerformer();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Worker getPerformer() {
        return performer;
    }

    public void setPerformer(Worker performer) {
        this.performer = performer;
    }
}
