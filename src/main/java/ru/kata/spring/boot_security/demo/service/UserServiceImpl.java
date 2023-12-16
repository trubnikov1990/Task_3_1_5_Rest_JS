package ru.kata.spring.boot_security.demo.service;

import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void addUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional
  public void deleteUser(int id) {
    userRepository.delete(userRepository.findById(id).orElseThrow(EntityNotFoundException::new));
  }

  @Override
  @Transactional
  public void updateUser(int id, User user) {
    user.setId(id);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    userRepository.save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public User showUser(int id) {
    return userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> listUsers() {
    return userRepository.findAll();
  }
}
