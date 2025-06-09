package com.phase.todoList.service.implemntation;

import com.phase.todoList.DTO.TaskRequest;
import com.phase.todoList.model.Priority;
import com.phase.todoList.model.Tasks;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Component
public interface TaskService {


    List<Tasks> getAllTasks();

    Tasks getTaskById(Long id);

    void addTask(TaskRequest request);

    void deleteTaskById(Long id);

    void updateTask(@Valid TaskRequest request,Long id);

    List<Tasks> getTaskWithPriority(Priority priority);

    Tasks completeTask(Long id);

    List<Tasks> completedTasks();

    List<Tasks> pendingTasks();

    List<Tasks> filterByDate(LocalDate from, LocalDate to);
}
