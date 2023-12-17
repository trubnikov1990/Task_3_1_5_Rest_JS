package ru.kata.spring.boot_security.demo.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "username")
  @NotEmpty(message = "Name is not empty")
  @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters")
  private String username;

  @Column(name = "name")
  @NotEmpty(message = "name is not empty")
  @Size(min = 2, max = 255, message = "name should be from 2 to 100 characters")
  private String name;

  @Column(name = "lastname")
  @NotEmpty(message = "lastname is not empty")
  @Size(min = 2, max = 255, message = "lastname should be from 2 to 100 characters")
  private String lastName;

  @Column(name = "email")
  @NotEmpty(message = "Email is not empty")
  @Email(message = "Email should be valid")
  private String email;

  @Column(name = "age")
  @Min(value = 0, message = "Возраст не может быть меньше 0 лет!")
  @Max(value = 127, message = "Возраст не может быть больше 127 лет!")
  private byte age;

  @Column(name = "password")
  @NotEmpty(message = "Password is not empty")
  private String password;

  public User() {
  }

  @ManyToMany
  @JoinTable(
      name = "user_role",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private List<Role> roleList;

  public User(int id, String username, String name, String lastName, String email, byte age, String password,
      List<Role> roleList) {
    this.id = id;
    this.username = username;
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.age = age;
    this.password = password;
    this.roleList = roleList;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roleList.stream().map(role -> new SimpleGrantedAuthority(role.getStatus()))
        .collect(Collectors.toList());
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Role> getRoleList() {
    return roleList;
  }

  public void setRoleList(List<Role> roleList) {
    this.roleList = roleList;
  }

  public byte getAge() {
    return age;
  }

  public void setAge(byte age) {
    this.age = age;
  }
}
