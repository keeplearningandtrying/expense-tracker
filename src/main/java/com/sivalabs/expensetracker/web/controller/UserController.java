package com.sivalabs.expensetracker.web.controller;

import static org.springframework.http.HttpStatus.CREATED;

import com.sivalabs.expensetracker.entity.User;
import com.sivalabs.expensetracker.model.UserDTO;
import com.sivalabs.expensetracker.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("")
  public List<UserDTO> getUsers() {
    log.info("process=get-users");
    List<User> allUsers = userService.getAllUsers();
    return allUsers.stream().map(this::toDTO).collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
    log.info("process=get-user, user_id={}", id);
    Optional<User> user = userService.getUserById(id);
    return user.map(u -> ResponseEntity.ok(toDTO(u))).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("")
  @ResponseStatus(CREATED)
  public UserDTO createUser(@RequestBody UserDTO user) {
    log.info("process=create-user, user_email={}", user.getEmail());
    return toDTO(userService.createUser(toEntity(user)));
  }

  @PutMapping("/{id}")
  public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
    log.info("process=update-user, user_id={}", id);
    User user = toEntity(userDTO);
    user.setId(id);
    return toDTO(userService.updateUser(user));
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    log.info("process=delete-user, user_id={}", id);
    userService.deleteUser(id);
  }

  public UserDTO toDTO(User user) {
    return UserDTO.builder()
        .id(user.getId())
        .name(user.getName())
        .username(user.getUsername())
        .password(user.getPassword())
        .email(user.getEmail())
        .enabled(user.isEnabled())
        .lastPasswordResetDate(user.getLastPasswordResetDate())
        .roles(user.getRoles())
        .build();
  }

  public User toEntity(UserDTO dto) {
    return User.builder()
        .id(dto.getId())
        .name(dto.getName())
        .username(dto.getUsername())
        .password(dto.getPassword())
        .email(dto.getEmail())
        .enabled(dto.isEnabled())
        .lastPasswordResetDate(dto.getLastPasswordResetDate())
        .roles(dto.getRoles())
        .build();
  }
}
