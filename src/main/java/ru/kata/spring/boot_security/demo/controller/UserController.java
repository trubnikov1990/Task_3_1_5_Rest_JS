package ru.kata.spring.boot_security.demo.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserSecurityService;

@Controller
public class UserController {

  private final UserSecurityService userDetail;

  @Autowired
  public UserController(UserSecurityService userDetail) {
    this.userDetail = userDetail;
  }

  @GetMapping("/user")
  public String infoAboutUser(Model model, Principal principal) {
    model.addAttribute("user", userDetail.findByEmail(principal.getName()));
    return "user/user";
  }
}





