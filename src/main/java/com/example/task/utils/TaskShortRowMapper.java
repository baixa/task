package com.example.task.utils;

import com.example.task.model.Task;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaskShortRowMapper implements RowMapper<Task> {
    @Override
    public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
        Task task = new Task();

        task.setId(rs.getLong("ID"));
        task.setStatus(rs.getString("STATUS"));
        task.setDescription(rs.getString("STATUS"));
        task.setTime(rs.getLong("TIME"));
        task.setTitle(rs.getString("TITLE"));

        return task;
    }
}
