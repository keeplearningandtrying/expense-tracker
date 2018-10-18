package com.sivalabs.expensetracker.web.controller;

import com.sivalabs.expensetracker.config.CurrentUser;
import com.sivalabs.expensetracker.entity.Transaction;
import com.sivalabs.expensetracker.entity.User;
import com.sivalabs.expensetracker.model.TransactionDTO;
import com.sivalabs.expensetracker.security.SecurityUtils;
import com.sivalabs.expensetracker.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/transactions")
@Slf4j
@PreAuthorize("isAuthenticated()")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired private CurrentUser currentUser;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @GetMapping("")
  public List<TransactionDTO> getUserTransactions() {
    log.info("current-user=" + currentUser.getId());
    log.info("process=get-user-transactions");
    return transactionService
        .getUserTransactions(currentUser.getId())
        .stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  @GetMapping("/{id}")
  public ResponseEntity<TransactionDTO> getTransaction(@PathVariable("id") Long id) {
    log.info("process=get-transaction, transaction_id={}", id);
    Optional<Transaction> transaction = transactionService.getTransactionById(id);
    return transaction
        .map(u -> ResponseEntity.ok(toDTO(u)))
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("")
  @ResponseStatus(CREATED)
  public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
    log.info("process=create-transaction");
    Transaction transaction = toEntity(transactionDTO);
    transaction.setUser(SecurityUtils.getLoginUser().getUser());
    return toDTO(transactionService.createTransaction(transaction));
  }

  @PutMapping("/{id}")
  public TransactionDTO updateTransaction(
      @PathVariable("id") Long id, @RequestBody TransactionDTO transactionDTO) {
    log.info("process=update-transaction, transaction_id={}", id);
    Transaction transaction = toEntity(transactionDTO);
    transaction.setId(id);
    transaction.setUser(SecurityUtils.getLoginUser().getUser());
    return toDTO(transactionService.updateTransaction(transaction));
  }

  @DeleteMapping("/{id}")
  public void deleteTransaction(@PathVariable("id") Long id) {
    log.info("process=delete-transaction, transaction_id={}", id);
    transactionService.deleteTransaction(id);
  }

  private TransactionDTO toDTO(Transaction txn) {
    return TransactionDTO.builder()
        .id(txn.getId())
        .amount(txn.getAmount())
        .type(txn.getType())
        .createdOn(txn.getCreatedOn())
        .userId(txn.getUser().getId())
        .build();
  }

  private Transaction toEntity(TransactionDTO dto) {
    return Transaction.builder()
        .id(dto.getId())
        .amount(dto.getAmount())
        .type(dto.getType())
        .createdOn(dto.getCreatedOn())
        .user(User.builder().id(dto.getUserId()).build())
        .build();
  }
}
