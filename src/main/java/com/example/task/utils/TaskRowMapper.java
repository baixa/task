package com.example.task.utils;

import com.example.task.model.Task;
import com.example.task.model.Worker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();

        task.setId(rs.getLong("TASK_ID"));
        task.setStatus(rs.getString("TASK_STATUS"));
        task.setDescription(rs.getString("TASK_STATUS"));
        task.setTime(rs.getLong("TASK_TIME"));
        task.setTitle(rs.getString("TASK_TITLE"));

        Worker worker = new Worker();
        worker.setId(rs.getLong("WORKER_ID"));
        worker.setName(rs.getString("WORKER_NAME"));
        worker.setPosition(rs.getString("WORKER_POSITION"));
        worker.setAvatar(rs.getBytes("WORKER_AVATAR"));

        task.setPerformer(worker);

        return task;
    }
}