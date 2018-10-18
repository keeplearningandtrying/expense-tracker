package com.sivalabs.expensetracker.security;

import lombok.EqualsAndHashCode;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.stream.Collectors;

@EqualsAndHashCode
public class SecurityUser extends User {

  private com.sivalabs.expensetracker.entity.User user;

  public SecurityUser(com.sivalabs.expensetracker.entity.User user) {
    super(
        user.getUsername(),
        user.getPassword(),
        user.getRoles()
            .stream()
            .map(r -> new SimpleGrantedAuthority(r.getName()))
            .collect(Collectors.toList()));
    this.user = user;
  }

  public com.sivalabs.expensetracker.entity.User getUser() {
    return user;
  }
}
