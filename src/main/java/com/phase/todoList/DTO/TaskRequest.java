package com.phase.todoList.DTO;

import com.phase.todoList.model.Priority;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class TaskRequest {
    @NotBlank(message = "Tile required!")
    private String title;
    private String description;
    @NotNull(message = "Priority is required!")
    private Priority taskPriority;
    @NotNull(message = "Due date is Required")
    @FutureOrPresent(message = "Due Date must be today or in future")
    private LocalDate dueDate;
    private Boolean completed = false;
}
