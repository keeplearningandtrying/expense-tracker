package com.sivalabs.expensetracker.model;

import com.sivalabs.expensetracker.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDTO {
    private Long id;
    private BigDecimal amount;
    private Transaction.Type type;
    private LocalDate createdOn;
    private Long userId;
}
