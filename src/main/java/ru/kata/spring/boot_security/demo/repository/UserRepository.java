package ru.kata.spring.boot_security.demo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);
}
