package com.example.task.utils;

import com.example.task.model.Task;
import org.springframework.jdbc.core.JdbcTemplate;

public class TaskThread implements Runnable {
    private final Task task;
    private final JdbcTemplate jdbcTemplate;

    public TaskThread(Task task, JdbcTemplate jdbcTemplate)
    {
        this.task = task;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run()
    {
        jdbcTemplate.update("INSERT INTO task (title, description, status, time, performer) " +
                        "VALUES (?, ?, ?, ?, ?)",
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getTime(),
                task.getPerformer().getId());
    }
}
