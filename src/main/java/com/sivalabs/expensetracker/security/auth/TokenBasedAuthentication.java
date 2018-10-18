package com.sivalabs.expensetracker.security.auth;

import lombok.EqualsAndHashCode;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

@EqualsAndHashCode
public class TokenBasedAuthentication extends AbstractAuthenticationToken {

  private final UserDetails principle;

  private String token;

  public TokenBasedAuthentication(String token, UserDetails principle) {
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
