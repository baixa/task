package com.example.task.utils;

import com.example.task.model.Worker;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerRowMapper implements RowMapper<Worker> {
    @Override
    public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {

        Worker worker = new Worker();
        worker.setId(rs.getLong("ID"));
        worker.setName(rs.getString("NAME"));
        worker.setPosition(rs.getString("POSITION"));
        worker.setAvatar(rs.getBytes("AVATAR"));

        return worker;
    }
}
