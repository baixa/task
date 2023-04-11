package com.example.task.controllers;

import com.example.task.model.Worker;
import com.example.task.model.dto.ResponseWorker;
import com.example.task.services.WorkerService;
import com.example.task.utils.NumParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/worker")
public class WorkerController {

    private final WorkerService workerService;

    public WorkerController(WorkerService workerService) {
        this.workerService = workerService;
    }

    @PostMapping
    public ResponseEntity<?> saveWorker(@RequestParam String name, @RequestParam String position,
                                        @RequestParam MultipartFile avatar) {
        Worker worker = new Worker();
        worker.setName(name);
        worker.setPosition(position);
        try {
            worker.setAvatar(avatar.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect image file");
        }
        workerService.saveWorker(worker);
        return ResponseEntity.status(HttpStatus.CREATED).body("Worker created");
    }

    @GetMapping("/all")
    public List<ResponseWorker> getAllWorkers() {
        List<Worker> workers = workerService.getAllWorkers();
        return workers.stream().map(ResponseWorker::new).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkerByTask(@PathVariable String id) {
        Long idValue = NumParser.parseStringAsLong(id);
        if (idValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Worker> worker = workerService.getWorkerById(idValue);
        return ResponseEntity.of(worker);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editWorker(@RequestParam String name, @RequestParam String position,
                                        @PathVariable String id, @RequestParam MultipartFile avatar) {
        Long idValue = NumParser.parseStringAsLong(id);
        if (idValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Worker> optionalWorker = workerService.getWorkerById(idValue);
        if (optionalWorker.isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Worker with this id is absent");


        Worker worker = new Worker();
        worker.setName(name);
        worker.setPosition(position);
        try {
            worker.setAvatar(avatar.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect image file");
        }
        worker.setId(idValue);
        workerService.editWorker(worker);

        return ResponseEntity.status(HttpStatus.OK).body("Entity was edited");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorker(@PathVariable String id) {
        Long idValue = NumParser.parseStringAsLong(id);
        if (idValue == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect id value");

        Optional<Worker> optionalWorker = workerService.getWorkerById(idValue);
        if (optionalWorker.isPresent()) {
            workerService.removeWorkerById(idValue);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Entity was removed");
    }


}
