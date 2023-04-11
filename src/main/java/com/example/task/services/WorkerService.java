package com.example.task.services;

import com.example.task.model.Worker;
import com.example.task.utils.WorkerRowMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkerService {

    private final JdbcTemplate jdbcTemplate;

    public WorkerService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Worker> getWorkerById(Long id) {
        try {
            Worker worker = jdbcTemplate.queryForObject("SELECT ID as WORKER_ID, NAME as WORKER_NAME, " +
                    "POSITION as WORKER_POSITION, AVATAR as WORKER_AVATAR FROM Worker WHERE ID = ?",
                    new Object[]{id},
                    new WorkerRowMapper());
            return Objects.isNull(worker) ? Optional.empty() : Optional.of(worker);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Worker> getAllWorkers() {
        return jdbcTemplate.query("SELECT ID as WORKER_ID, NAME as WORKER_NAME, " +
                        "POSITION as WORKER_POSITION, AVATAR as WORKER_AVATAR FROM Worker",
                new WorkerRowMapper());
    }
}
