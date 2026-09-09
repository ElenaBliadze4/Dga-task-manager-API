package com.savarjishodga.dgataskebismartva.repository;

import com.savarjishodga.dgataskebismartva.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
