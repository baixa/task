package com.example.task.services;

import com.example.task.model.Task;
import com.example.task.utils.TaskRowMapper;
import com.example.task.utils.TaskShortRowMapper;
import com.example.task.utils.TaskThread;
import com.example.task.utils.ThreadPoolUtil;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TaskService {

    private final JdbcTemplate jdbcTemplate;
    private final ThreadPoolUtil threadPoolUtil;

    public TaskService(JdbcTemplate jdbcTemplate, ThreadPoolUtil threadPoolUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.threadPoolUtil = threadPoolUtil;
    }

    public List<Task> getAllTasks() {
        return jdbcTemplate.query(
                "SELECT t.ID AS TASK_ID, t.TITLE AS TASK_TITLE, " +
                        "t.DESCRIPTION AS TASK_DESCRIPTION, t.STATUS AS TASK_STATUS, t.TIME AS TASK_TIME, w.ID as WORKER_ID, " +
                        "w.NAME as WORKER_NAME, w.POSITION as WORKER_POSITION, w.AVATAR as WORKER_AVATAR " +
                        "FROM Task as t INNER JOIN Worker w ON t.performer = w.id",
                new TaskRowMapper());
    }

    public Optional<Task> getTaskById(Long taskId) {
        try {
            Task task = jdbcTemplate.queryForObject(
                    "SELECT t.ID AS TASK_ID, t.TITLE AS TASK_TITLE, " +
                            "t.DESCRIPTION AS TASK_DESCRIPTION, t.STATUS AS TASK_STATUS, t.TIME AS TASK_TIME, w.ID as WORKER_ID, " +
                            "w.NAME as WORKER_NAME, w.POSITION as WORKER_POSITION, w.AVATAR as WORKER_AVATAR " +
                            "FROM Task as t INNER JOIN Worker w ON t.performer = w.id " +
                            "WHERE t.id = ?",
                    new Object[]{taskId},
                    new TaskRowMapper());
            return Objects.isNull(task) ? Optional.empty() : Optional.of(task);
        }
        catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Task> getTaskByPerformerId(Long performerId) {
        return jdbcTemplate.query(
                "SELECT ID, t.TITLE, t.DESCRIPTION, t.STATUS, t.TIME " +
                        "FROM Task as t WHERE t.performer = ?",
                new Object[]{performerId},
                new TaskShortRowMapper());
    }

    public void saveTask(Task task) {
        threadPoolUtil.executeTask(new TaskThread(task, jdbcTemplate));
    }

    public void editTask(Task editedTask) {
        jdbcTemplate.update("UPDATE task SET title = ?, description = ?, status = ?, time = ? " +
                        "WHERE id = ?",
                editedTask.getTitle(),
                editedTask.getDescription(),
                editedTask.getStatus(),
                editedTask.getTime(),
                editedTask.getId());
    }

    public void editTaskPerformer(Task editedTask) {
        jdbcTemplate.update("UPDATE task SET performer = ? WHERE id = ?",
                editedTask.getPerformer().getId(),
                editedTask.getId());
    }

    public void removeTasksByPerformerId(Long id) {
        jdbcTemplate.update("DELETE FROM task WHERE performer = ?", id);
    }
}
