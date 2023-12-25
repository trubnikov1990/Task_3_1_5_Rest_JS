package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import ru.kata.spring.boot_security.demo.model.User;

public interface UserService {

  List<User> findAll();

  User findByUsername(String username);

  User findUserById(Long id);

  void update(User user);

  void saveUser(User user);

  boolean deleteUserById(Long id);

}