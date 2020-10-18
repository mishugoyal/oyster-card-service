package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Optional;

@Getter
@Setter
@ToString
public class Transaction {
  private TransactionType transactionType;
  private double amount;
  private Journey journey = null;

  public Optional<Journey> getJourney() {
    return Optional.ofNullable(journey);
  }
}
