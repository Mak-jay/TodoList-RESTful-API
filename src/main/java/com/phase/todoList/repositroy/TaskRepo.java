package com.phase.todoList.repositroy;

import com.phase.todoList.model.Priority;
import com.phase.todoList.model.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Tasks,Long> {
    @Query("Select t from Tasks t WHERE t.taskPriority= ?1")
    List<Tasks> findTasksWithPriority(Priority priority);

    @Query("Select t from Tasks t WHERE t.complete = true")
    Optional<List<Tasks>> findCompleted();

    @Query("Select t from Tasks t WHERE t.complete = false")
    Optional<List<Tasks>> findPending();

    @Query("Select t from Tasks t WHERE t.dueDate BETWEEN :from AND :to")
    Optional<List<Tasks>> findByDateBetween(@Param("from") LocalDate from,@Param("to") LocalDate to);
}
