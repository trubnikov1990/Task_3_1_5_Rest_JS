package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/user")
public class RestUserController {
  private final UserService userService;

  @Autowired
  public RestUserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/showAccount")
  public ResponseEntity<User> showUserAccount(Principal principal) {
    return new ResponseEntity<>(userService.findByUsername(principal.getName()), HttpStatus.OK);
  }
}






