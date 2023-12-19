package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserSecurityService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

  private final UserService userService;
  private final RoleService roleService;
  private final UserSecurityService userDetail;

  @Autowired
  public AdminController(UserService userService, RoleService roleService,
      UserSecurityService userDetail) {
    this.userService = userService;
    this.roleService = roleService;
    this.userDetail = userDetail;
  }

  @GetMapping
  public String getAllUser(Model model, Principal principal) {
    model.addAttribute("user", userDetail.findByName(principal.getName()));
    model.addAttribute("listOfUsers", userService.listUsers());
    model.addAttribute("allRoles", roleService.getAll());
    return "admin";
  }

  @PostMapping()
  public String saveNewUser(@ModelAttribute("user") User user) {
    userService.addUser(user);
    return "redirect:/admin";
  }

  @PostMapping("/edit")
  public String update(@ModelAttribute("user") User user, @RequestParam(value = "id") int id) {
    userService.updateUser(id, user);
    return "redirect:/admin";
  }

  @PostMapping("/delete")
  public String deleteUser(@RequestParam(value = "id") int id) {
    userService.deleteUser(id);
    return "redirect:/admin";
  }
}


