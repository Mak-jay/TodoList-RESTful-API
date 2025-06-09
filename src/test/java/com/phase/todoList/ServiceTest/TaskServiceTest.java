package com.phase.todoList.ServiceTest;

import com.phase.todoList.DTO.TaskRequest;
import com.phase.todoList.model.Priority;
import com.phase.todoList.model.Tasks;
import com.phase.todoList.repositroy.TaskRepo;
import com.phase.todoList.service.TaskServiceImpl;
import com.phase.todoList.service.implemntation.TaskService;
import  org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static com.phase.todoList.model.Priority.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepo taskRepo;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    void testGetTaskById_ShouldReturnTask(){

        Tasks mockTask = Tasks.builder()
                .id(1L)
                .title("Test")
                .description("Learn Testing")
                .dueDate(LocalDate.now())
                .taskPriority(HIGH)
                .complete(false)
                .build();

        Mockito.when(taskRepo.findById(mockTask.getId())).thenReturn(Optional.of(mockTask));

        Tasks taskFound = taskService.getTaskById(mockTask.getId());

        Assertions.assertNotNull(taskFound);
        Assertions.assertEquals(mockTask.getId(),taskFound.getId());
        Assertions.assertEquals("Test",taskFound.getTitle());
        Assertions.assertEquals(HIGH,taskFound.getTaskPriority());
    }

    @Test
    void testAddTaskShould_AddTask(){
        TaskRequest mockTask = TaskRequest.builder()
                .title("Read book")
                .description("Read Law nuumber 16")
                .taskPriority(MEDIUM)
                .dueDate(LocalDate.now())
                .build();

        taskService.addTask(mockTask);

        ArgumentCaptor<Tasks> taskCaptor = ArgumentCaptor.forClass(Tasks.class);
        Mockito.verify(taskRepo, Mockito.times(1)).save(taskCaptor.capture());

        Tasks savedTask = taskCaptor.getValue();
        Assertions.assertEquals("Read book",savedTask.getTitle());

    }

}
