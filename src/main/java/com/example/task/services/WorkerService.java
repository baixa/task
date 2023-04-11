package com.example.task.services;

import com.example.task.model.Task;
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
    private final TaskService taskService;

    public WorkerService(JdbcTemplate jdbcTemplate, TaskService taskService) {
        this.jdbcTemplate = jdbcTemplate;
        this.taskService = taskService;
    }

    public Optional<Worker> getWorkerById(Long id) {
        try {
            Worker worker = jdbcTemplate.queryForObject("SELECT ID, NAME, " +
                    "POSITION, AVATAR FROM Worker WHERE ID = ?",
                    new Object[]{id},
                    new WorkerRowMapper());

            if (Objects.isNull(worker))
                return Optional.empty();
            else {
                List<Task> tasks = taskService.getTaskByPerformerId(worker.getId());
                worker.setTasks(tasks);
                return Optional.of(worker);
            }
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Worker> getAllWorkers() {
        List<Worker> workers = jdbcTemplate.query("SELECT ID, NAME, " +
                        "POSITION, AVATAR FROM Worker",
                new WorkerRowMapper());
        for (Worker worker : workers) {
            List<Task> tasks = taskService.getTaskByPerformerId(worker.getId());
            worker.setTasks(tasks);
        }
        return workers;
    }

    public void saveWorker(Worker worker) {
        jdbcTemplate.update("INSERT INTO worker (name, position, avatar) " +
                        "VALUES (?, ?, ?)",
                worker.getName(),
                worker.getPosition(),
                worker.getAvatar()
        );
    }

    public void editWorker(Worker worker) {
        jdbcTemplate.update("UPDATE worker SET name = ?, position = ?, avatar = ? " +
                        "WHERE id = ?",
                worker.getName(),
                worker.getPosition(),
                worker.getAvatar(),
                worker.getId());
    }

    public void removeWorkerById(Long idValue) {
        List<Task> tasks = taskService.getTaskByPerformerId(idValue);
        if (!tasks.isEmpty()) {
            taskService.removeTasksByPerformerId(idValue);
        }

        jdbcTemplate.update("DELETE FROM worker WHERE id = ?", idValue);
    }
}
