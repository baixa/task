package com.example.task.model.dto;

public class EditedTask {
    private String title;
    private String description;
    private Long time;
    private String status;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
