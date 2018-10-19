package com.sivalabs.expensetracker.security.auth;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode
public class JWTAuthenticationToken extends AbstractAuthenticationToken {

  private final UserDetails principle;

  private String token;

  public JWTAuthenticationToken(String token, UserDetails principle) {
    super(principle.getAuthorities());
    this.token = token;
    this.principle = principle;
  }

  @Override
  public boolean isAuthenticated() {
    return true;
  }

  @Override
  public Object getCredentials() {
    return token;
  }

  @Override
  public UserDetails getPrincipal() {
    return principle;
  }
}
