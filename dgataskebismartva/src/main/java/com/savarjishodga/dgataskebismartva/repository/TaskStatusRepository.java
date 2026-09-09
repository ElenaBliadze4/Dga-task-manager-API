package com.savarjishodga.dgataskebismartva.repository;


import com.savarjishodga.dgataskebismartva.entity.TaskStatus;
import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus,Long> {
    Optional<TaskStatus> findByCode(TaskStatusEnum code);
}
