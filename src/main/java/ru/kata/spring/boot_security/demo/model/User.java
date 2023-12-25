package ru.kata.spring.boot_security.demo.model;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(name = "username")
  @NotEmpty(message = "Name is not empty")
  @Size(min = 2, max = 100, message = "Name should be from 2 to 100 characters")
  private String username;


  @Column(name = "lastname")
  @NotEmpty(message = "lastname is not empty")
  @Size(min = 2, max = 255, message = "lastname should be from 2 to 100 characters")
  private String lastname;


  @Column(name = "age")
  @Min(value = 0, message = "Возраст не может быть меньше 0 лет!")
  @Max(value = 127, message = "Возраст не может быть больше 127 лет!")
  private Byte age;

  @Email
  @Column(name = "email")
  private String email;

  @Column(name = "password")
  @NotEmpty(message = "Password is not empty")
  private String password;

  @ManyToMany
  @JoinTable(name = "users_roles",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))

  private Collection <Role> roles;

  public User() {
  }

  public User(String username, String lastname, Byte age, String email, String password, List<Role> roles) {
    this.username = username;
    this.lastname = lastname;
    this.age = age;
    this.email = email;
    this.password = password;
    this.roles = roles;
  }

  public Long getId() {
    return id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }


  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }


  public Byte getAge() {
    return age;
  }

  public void setAge(Byte age) {
    this.age = age;
  }


  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }


  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }


  public Collection<Role> getRoles() {
    return roles;
  }

  public void setRoles(Collection<Role> roles) {
    this.roles = roles;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return username.equals(user.username) && lastname.equals(user.lastname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(username, lastname);
  }
}
