package com.savarjishodga.dgataskebismartva.repository;

import com.savarjishodga.dgataskebismartva.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
