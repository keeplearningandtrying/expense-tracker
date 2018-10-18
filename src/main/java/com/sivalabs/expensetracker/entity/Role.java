package com.sivalabs.expensetracker.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "roles")
@Data
public class Role {

  @Id
  @SequenceGenerator(name = "role_id_generator", sequenceName = "role_id_seq")
  @GeneratedValue(generator = "role_id_generator")
  Long id;

  @Column(name = "name", unique = true)
  String name;
}
