package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserDetailsService userDetailsService;
  private final SuccessUserHandler successUserHandler;

  @Autowired
  public WebSecurityConfig(UserDetailsService userDetailsService, SuccessUserHandler successUserHandler) {
    this.userDetailsService = userDetailsService;
    this.successUserHandler = successUserHandler;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/index", "/login").permitAll()
        .antMatchers("/api/user/**").hasAnyRole("USER", "ADMIN")
        .antMatchers("/api/admin/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and()

        .formLogin().successHandler(successUserHandler).permitAll()
        .and()

        .logout().permitAll();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) {
    auth.authenticationProvider(authenticationProvider());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setPasswordEncoder(passwordEncoder()); //Кодировщик, чтобы преобразовать введенный с формы пароль к хэшу, т.е. к тому виду пароля, который лежит в БД
    authenticationProvider.setUserDetailsService(userDetailsService);
    return authenticationProvider;
  }
}