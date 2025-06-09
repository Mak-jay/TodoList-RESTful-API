package com.phase.todoList.service;

import com.phase.todoList.DTO.TaskRequest;
import com.phase.todoList.exception.GlobalExceptionHandler;
import com.phase.todoList.exception.NoSuchTaskExistException;
import com.phase.todoList.model.Priority;
import com.phase.todoList.model.Tasks;
import com.phase.todoList.repositroy.TaskRepo;
import com.phase.todoList.service.implemntation.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private GlobalExceptionHandler exceptionHandler;
    @Autowired
    private TaskRepo taskRepo;

    @Override
    public List<Tasks> getAllTasks() {
        return taskRepo.findAll();
    }

    @Override
    public Tasks getTaskById(Long id) {
        log.info("Fetching task by ID:{}",id);
        return taskRepo.findById(id).orElseThrow(()-> {log.warn("Task with ID {} not found",id);
        return new NoSuchTaskExistException("Task not found");
        });
    }

    @Override
    public void addTask(TaskRequest request) {
        log.info("Adding new Task with title: {}",request.getTitle());
        Tasks task = new Tasks();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setTaskPriority(request.getTaskPriority());
        task.setDueDate(request.getDueDate());
        task.setComplete(request.getCompleted() != null ? request.getCompleted() : false);

        taskRepo.save(task);
    }

    @Override
    public void deleteTaskById(Long id) {
        log.info("Task with ID {} is deleted",id);
        taskRepo.deleteById(id);
    }

    @Override
    public void updateTask(TaskRequest request,Long id) {
        Tasks task = taskRepo.findById(id).orElseThrow(() -> new NoSuchTaskExistException("No such task exist with id = "+id));
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setTaskPriority(request.getTaskPriority());
        task.setDueDate(request.getDueDate());
        task.setComplete(request.getCompleted() != null ? request.getCompleted() : false);

        taskRepo.save(task);
    }

    @Override
    public List<Tasks> getTaskWithPriority(Priority priority) {
        List<Tasks> tasksList = taskRepo.findTasksWithPriority(priority);

        if (tasksList.isEmpty()){
            throw new NoSuchTaskExistException("No tasks with High priority exist");
        }else {
            return tasksList;
        }
    }

    @Override
    public Tasks completeTask(Long id) {
        Tasks completeTask = taskRepo.findById(id).orElseThrow(() ->
                new NoSuchTaskExistException("There is no such task with id : "+id));
        completeTask.setComplete(true);
        taskRepo.save(completeTask);
        return completeTask;
    }

    @Override
    public List<Tasks> completedTasks() {
        List<Tasks> completedTasks;
        completedTasks = taskRepo.findCompleted().orElseThrow(()->
                new NoSuchTaskExistException("None of tasks are completed yet"));
        return completedTasks;
    }

    @Override
    public List<Tasks> pendingTasks() {
        List<Tasks> pendingTasks;
                pendingTasks = taskRepo.findPending().orElseThrow(() ->
                        new NoSuchTaskExistException("No Task are pending"));
        return pendingTasks;
    }

    @Override
    public List<Tasks> filterByDate(LocalDate from, LocalDate to) {
        List<Tasks> filteredTasks;
        filteredTasks= taskRepo.findByDateBetween(from,to).orElseThrow(
                () -> new NoSuchTaskExistException("No Tasks exist between these dates."));
        return filteredTasks;
    }
}
