package com.phase.todoList.controller;

import com.phase.todoList.DTO.TaskRequest;
import com.phase.todoList.model.Priority;
import com.phase.todoList.model.Tasks;
import com.phase.todoList.service.implemntation.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/todos")
public class TaskController {
    @Autowired
    private TaskService service;

    @GetMapping
    public ResponseEntity<List<Tasks>> AllTasks(){
        List<Tasks> allTasks= service.getAllTasks();
        return ResponseEntity.ok(allTasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tasks> tasksById(@PathVariable Long id){
        Tasks taskById = service.getTaskById(id);
        return ResponseEntity.ok(taskById);
    }

    @GetMapping("/priority")
    public ResponseEntity<List<Tasks>> tasksWithPriority(@RequestParam("priority") Priority priority){
        List<Tasks> highPriorityTasks = service.getTaskWithPriority(priority);
        return  ResponseEntity.ok(highPriorityTasks);
    }
    @GetMapping("/completed")
    public ResponseEntity<List<Tasks>> completedTasks(){
        List<Tasks> completed = service.completedTasks();
        return ResponseEntity.ok(completed);
    }
    @GetMapping("/pending")
    public ResponseEntity<List<Tasks>> pendingTasks(){
        List<Tasks> pending = service.pendingTasks();
        return ResponseEntity.ok(pending);
    }
    @GetMapping("/due")
    public ResponseEntity<List<Tasks>> filterByDate(@RequestParam LocalDate from,@RequestParam LocalDate to){
        List<Tasks> filteredTasks = service.filterByDate(from,to);
        return ResponseEntity.ok(filteredTasks);
    }

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody TaskRequest request){
        service.addTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}/toggle")
    public ResponseEntity<Tasks> completeTask(@PathVariable Long id){
        Tasks completedTask = service.completeTask(id);
        return ResponseEntity.ok(completedTask);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@Valid @RequestBody TaskRequest request,@PathVariable Long id){
        service.updateTask(request,id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id){
        service.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

}
