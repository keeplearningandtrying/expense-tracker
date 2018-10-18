package com.sivalabs.expensetracker.model;

import com.sivalabs.expensetracker.entity.Role;
import java.sql.Timestamp;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
  private Long id;

  private String username;

  private String password;

  private String name;

  private String email;

  private boolean enabled;

  private Timestamp lastPasswordResetDate;

  private List<Role> roles;
}
