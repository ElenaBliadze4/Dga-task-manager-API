package com.savarjishodga.dgataskebismartva.repository;

import com.savarjishodga.dgataskebismartva.entity.Task;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long> {
    List<Task> findByStatusCode(TaskStatusEnum code);
    List<Task> findByAssigneeId(Long userId);
    long countByProjectId(Long projectId);

    List<Task> findByProjectId(Long projectId);
}
