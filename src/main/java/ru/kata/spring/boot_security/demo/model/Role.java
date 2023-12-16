package ru.kata.spring.boot_security.demo.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "status")
  private String status;

  // empty constructor
  public Role() {
  }

  @ManyToMany(mappedBy = "roleList")
  private List<User> userList;

  public Role(int id, String status, List<User> userList) {
    this.id = id;
    this.status = status;
    this.userList = userList;
  }

  @Override
  public String getAuthority() {
    return status;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public List<User> getUserList() {
    return userList;
  }

  public void setUserList(List<User> userList) {
    this.userList = userList;
  }
}
