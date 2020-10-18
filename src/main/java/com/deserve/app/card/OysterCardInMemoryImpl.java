package com.deserve.app.card;

import com.deserve.app.fare.FareCalculator;
import com.deserve.app.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OysterCardInMemoryImpl implements OysterCard {
  private double balance;
  private final List<Transaction> transactions;
  private final FareCalculator fareCalculator;

  public OysterCardInMemoryImpl(FareCalculator fareCalculator) {
    balance = 0;
    transactions = new ArrayList<>();
    this.fareCalculator = fareCalculator;
  }

  @Override
  public boolean swipeIn(Station station, JourneyType journeyType) {
    Journey journey = getSwipeInJourney(station, journeyType);
    final Optional<Double> fare = fareCalculator.getFare(journey);
    if (fare.isPresent() && isSufficientBalance(fare.get())) {
      getJourneyTransaction(journey, fare.get());
      debitBalance(fare.get());
      return true;
    }
    return false;
  }

  @Override
  public boolean swipeOut(Station station) {
    Optional<Transaction> transaction = getLastSwipeInTransaction();
    if (transaction.isPresent()) {
      Journey journey = transaction.get().getJourney().get();
      journey.setDestination(station);
      final Optional<Double> fare = fareCalculator.getFare(journey);
      if (fare.isPresent()) {
        double previousFare = transaction.get().getAmount();
        transaction.get().setAmount(fare.get());
        creditBalance(previousFare - fare.get());
        return true;
      }
      return false;
    }
    return false;
  }

  @Override
  public double getBalance() {
    return this.balance;
  }

  @Override
  public double addBalance(double amount) {
    creditBalance(amount);
    transactions.add(getAddBalanceTransaction(amount));
    return this.balance;
  }

  @Override
  public List<Transaction> getTransactions() {
    return transactions;
  }

  private void creditBalance(double amount) {
    this.balance += amount;
  }

  private Transaction getAddBalanceTransaction(double amount) {
    Transaction transaction = new Transaction();
    transaction.setAmount(amount);
    transaction.setTransactionType(TransactionType.CREDIT);
    return transaction;
  }

  private void getJourneyTransaction(Journey journey, Double fare) {
    Transaction transaction = new Transaction();
    transaction.setAmount(fare);
    transaction.setTransactionType(TransactionType.DEBIT);
    transaction.setJourney(journey);
    transactions.add(transaction);
  }

  private void debitBalance(Double amount) {
    balance -= amount;
  }

  private boolean isSufficientBalance(Double fare) {
    return fare <= this.balance;
  }

  private Journey getSwipeInJourney(Station station, JourneyType journeyType) {
    Journey journey = new Journey();
    journey.setSource(station);
    journey.setJourneyType(journeyType);
    return journey;
  }

  private boolean isJourneyPresent(int index) {
    return transactions.get(index).getJourney().isPresent();
  }

  private boolean isDestinationPresent(int index) {
    return transactions.get(index).getJourney().get().getDestination().isPresent();
  }

  private Optional<Transaction> getLastSwipeInTransaction() {
    for (int i = transactions.size() - 1; i >= 0; i--) {
      if (isJourneyPresent(i) && isDestinationPresent(i)) return Optional.empty();
      if (isJourneyPresent(i) && !isDestinationPresent(i)) return Optional.of(transactions.get(i));
    }
    return Optional.empty();
  }
}
