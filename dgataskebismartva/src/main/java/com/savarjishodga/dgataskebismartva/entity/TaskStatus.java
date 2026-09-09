package com.savarjishodga.dgataskebismartva.entity;

import com.savarjishodga.dgataskebismartva.entity.statusenum.TaskStatusEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "task_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "code" , nullable = false, unique = true)
    private TaskStatusEnum code;
}
