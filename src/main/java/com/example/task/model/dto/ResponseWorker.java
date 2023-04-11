package com.example.task.model.dto;

import com.example.task.model.Worker;

import java.util.List;

public class ResponseWorker {
    private Long id;
    private String name;
    private String position;
    private byte[] avatar;
    private List<ShortResponseTask> taskList;

    public ResponseWorker (Worker worker) {
        this.id = worker.getId();
        this.name = worker.getName();
        this.position = worker.getPosition();
        this.avatar = worker.getAvatar();
        this.taskList = worker.getTasks().stream().map(ShortResponseTask::new).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public List<ShortResponseTask> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<ShortResponseTask> taskList) {
        this.taskList = taskList;
    }
}
