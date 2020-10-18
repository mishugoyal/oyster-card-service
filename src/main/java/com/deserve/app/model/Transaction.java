package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public class Transaction {
  private TransactionType transactionType;
  private double amount;
  private Journey journey = null;

  public Optional<Journey> getJourney() {
    return Optional.ofNullable(journey);
  }
}
