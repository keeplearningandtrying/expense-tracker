package com.sivalabs.expensetracker.web.controller;

import com.sivalabs.expensetracker.config.CurrentUser;
import com.sivalabs.expensetracker.entity.Transaction;
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
  public List<Transaction> getUserTransactions() {
    log.info("current-user=" + currentUser.getId());
    log.info("process=get-user-transactions");
    return transactionService.getUserTransactions(currentUser.getId());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable("id") Long id) {
    log.info("process=get-transaction, transaction_id={}", id);
    Optional<Transaction> transaction = transactionService.getTransactionById(id);
    return transaction.map(u -> ResponseEntity.ok(u)).orElse(ResponseEntity.notFound().build());
  }

  @PostMapping("")
  @ResponseStatus(CREATED)
  public Transaction createTransaction(@RequestBody Transaction transaction) {
    log.info("process=create-transaction");
    transaction.setUser(SecurityUtils.getLoginUser().getUser());
    return transactionService.createTransaction(transaction);
  }

  @PutMapping("/{id}")
  public Transaction updateTransaction(
      @PathVariable("id") Long id, @RequestBody Transaction transaction) {
    log.info("process=update-transaction, transaction_id={}", id);
    transaction.setId(id);
    transaction.setUser(SecurityUtils.getLoginUser().getUser());
    return transactionService.updateTransaction(transaction);
  }

  @DeleteMapping("/{id}")
  public void deleteTransaction(@PathVariable("id") Long id) {
    log.info("process=delete-transaction, transaction_id={}", id);
    transactionService.deleteTransaction(id);
  }
}
