package com.sivalabs.expensetracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @SequenceGenerator(name = "txn_id_generator", sequenceName = "txn_id_seq")
  @GeneratedValue(generator = "txn_id_generator")
  private Long id;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  private Type type;

  @Builder.Default
  private LocalDate createdOn = LocalDate.now();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public enum Type {
    INCOME,
    EXPENSE
  }
}
