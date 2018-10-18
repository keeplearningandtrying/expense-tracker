package com.sivalabs.expensetracker.model;

import com.sivalabs.expensetracker.entity.Transaction;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class TransactionDTO {
  private Long id;
  private BigDecimal amount;
  private Transaction.Type type;
  private LocalDate createdOn;
  private Long userId;
}
