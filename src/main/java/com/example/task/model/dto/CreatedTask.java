package com.example.task.model.dto;

import com.example.task.model.Worker;

public class CreatedTask {

    private String title;
    private String description;
    private Long time;
    private String status;
    private String performerId;
    private Worker performer;

    public CreatedTask(String title, String description, Long time, String status, String performerId) {
        this.title = title;
        this.description = description;
        this.time = time;
        this.status = status;
        this.performerId = performerId;
    }

    public CreatedTask() {
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

    public String getPerformerId() {
        return performerId;
    }

    public void setPerformerId(String performerId) {
        this.performerId = performerId;
    }

    public Worker getPerformer() {
        return performer;
    }

    public void setPerformer(Worker performer) {
        this.performer = performer;
    }
}
