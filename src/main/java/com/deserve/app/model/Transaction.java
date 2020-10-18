package com.deserve.app.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

import static com.deserve.app.model.TransactionType.CREDIT;

@Getter
@Setter
public class Transaction {
  private TransactionType transactionType;
  private double amount;
  private Journey journey = null;

  public Optional<Journey> getJourney() {
    return Optional.ofNullable(journey);
  }

  @Override
  public String toString() {
    if (CREDIT == transactionType) return "\n" + transactionType + " - Balance Loaded - " + amount;
    return "\n"
        + transactionType
        + " - "
        + journey.getSource().getName()
        + (journey.getDestination().isPresent()
            ? " to " + journey.getDestination().get().getName()
            : "")
        + "("
        + journey.getJourneyType()
        + ") - "
        + amount;
  }
}
