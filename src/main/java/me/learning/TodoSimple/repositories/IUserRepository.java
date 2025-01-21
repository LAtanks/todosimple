package me.learning.TodoSimple.repositories;

import me.learning.TodoSimple.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Transactional(readOnly = true)
    Optional<User> findByUsername(String username);
}
