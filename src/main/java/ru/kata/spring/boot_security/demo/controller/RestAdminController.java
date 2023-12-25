package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class RestAdminController {

  private final UserService userService;
  private final RoleService roleService;

  @Autowired
  public RestAdminController(UserService userService, RoleService roleService) {
    this.userService = userService;
    this.roleService = roleService;
  }

  @GetMapping("/showAccount")
  public ResponseEntity<User> showInfoUser(Principal principal) {
    return ResponseEntity.ok(userService.findByUsername(principal.getName()));
  }

  @GetMapping("/users")
  public ResponseEntity<List<User>> getAllUsers() {
    return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
  }

  @GetMapping(value = "/roles")
  public ResponseEntity<Collection<Role>> getAllRoles() {
    return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
  }

  @GetMapping("/roles/{id}")
  public ResponseEntity<Collection<Role>> getRole(@PathVariable("id") Long id) {
    return new ResponseEntity<>(userService.findUserById(id).getRoles(), HttpStatus.OK);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
    return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<User> addNewUser(@RequestBody @Valid User newUser) {
    userService.saveUser(newUser);
    return new ResponseEntity<>(newUser, HttpStatus.OK);

  }

  @PatchMapping("/users/{id}")
  public ResponseEntity<User> updateUser(@RequestBody User userFromWebPage) {
    userService.update(userFromWebPage);
    return new ResponseEntity<>(userFromWebPage, HttpStatus.OK);
  }

  @DeleteMapping("/users/{id}")
  public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id) {
    userService.findUserById(id);
    userService.deleteUserById(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}


