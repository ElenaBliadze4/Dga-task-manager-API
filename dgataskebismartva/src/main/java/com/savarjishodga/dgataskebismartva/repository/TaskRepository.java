package com.savarjishodga.dgataskebismartva.repository;

import com.savarjishodga.dgataskebismartva.entity.Task;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatusCode(TaskStatusEnum code);

    List<Task> findByAssigneeId(Long userId);

    long countByProjectId(Long projectId);

    List<Task> findByProjectId(Long projectId);

    // ფილტრაციისთვის

    @Query("SELECT DISTINCT t FROM Task t " +
            "LEFT JOIN FETCH t.assignee a " +
            "LEFT JOIN FETCH t.status s " +
            "LEFT JOIN FETCH t.project p " +
            "WHERE (CAST(:status AS string) IS NULL OR s.code = :status) " +
            "AND (CAST(:assignee AS string) IS NULL OR " +
            "     LOWER(a.firstName) LIKE LOWER(CAST(:assignee AS string)) OR " +
            "     LOWER(a.lastName) LIKE LOWER(CAST(:assignee AS string)) OR " +
            "     LOWER(CONCAT(a.firstName, ' ', a.lastName)) LIKE LOWER(CAST(:assignee AS string)) OR " +
            "     LOWER(CONCAT(a.lastName, ' ', a.firstName)) LIKE LOWER(CAST(:assignee AS string))) " +
            "AND (CAST(:title AS string) IS NULL OR " +
            "     LOWER(t.title) LIKE LOWER(CAST(:title AS string)))")
    List<Task> filterTasks(@Param("status") TaskStatusEnum status,
                           @Param("assignee") String assignee,
                           @Param("title") String title);
}