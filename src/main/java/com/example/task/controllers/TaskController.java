package com.example.task.controllers;

import com.example.task.model.Task;
import com.example.task.model.Worker;
import com.example.task.model.dto.CreatedTask;
import com.example.task.model.dto.EditedTask;
import com.example.task.model.dto.ShortResponseTask;
import com.example.task.services.TaskService;
import com.example.task.services.WorkerService;
import com.example.task.utils.NumParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskService taskService;
    private final WorkerService workerService;

    public TaskController(TaskService taskService, WorkerService workerService) {
        this.taskService = taskService;
        this.workerService = workerService;
    }

    @PostMapping
    public ResponseEntity<?> saveTask(@RequestBody CreatedTask createdTask) {
        Long idValue = NumParser.parseStringAsLong(createdTask.getPerformerId());
        if (idValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Worker> worker = workerService.getWorkerById(idValue);
        if (worker.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Performer with this id is absent");

        createdTask.setPerformer(worker.get());
        Task task = new Task(createdTask);
        taskService.saveTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body("Task add to queue");
    }

    @GetMapping("/all")
    public List<ShortResponseTask> getAllTasks() {
        List<Task> tasks = taskService.getAllTasks();
        return tasks.stream().map(ShortResponseTask::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable String id) {
        Long idValue = NumParser.parseStringAsLong(id);
        if (idValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Task> task = taskService.getTaskById(idValue);
        return ResponseEntity.of(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editTask(@RequestBody EditedTask editedTask, @PathVariable String id) {
        Long idValue = NumParser.parseStringAsLong(id);
        if (idValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Task> optionalTask = taskService.getTaskById(idValue);
        if (optionalTask.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with this id is absent");

        Task task = new Task(editedTask);
        task.setId(idValue);
        taskService.editTask(task);

        return ResponseEntity.status(HttpStatus.OK).body("Entity was edited");
    }

    @PutMapping("/{taskId}/performer")
    public ResponseEntity<?> editTaskPerformer(@RequestParam String performerId, @PathVariable String taskId) {
        Long idPerformerValue = NumParser.parseStringAsLong(performerId);
        Long idTaskValue = NumParser.parseStringAsLong(taskId);
        if (idTaskValue == null || idPerformerValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Worker> worker = workerService.getWorkerById(idPerformerValue);
        if (worker.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker with this id is absent");

        Optional<Task> task = taskService.getTaskById(idTaskValue);
        if (task.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task with this id is absent");

        task.get().setPerformer(worker.get());
        taskService.editTaskPerformer(task.get());

        return ResponseEntity.status(HttpStatus.OK).body("Entity was edited");
    }
}
