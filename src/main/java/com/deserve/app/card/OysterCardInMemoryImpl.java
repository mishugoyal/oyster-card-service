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
      reduceBalance(fare.get());
      return true;
    }
    return false;
  }

  @Override
  public boolean swipeOut(Station station) {
    return false;
  }

  @Override
  public double getBalance() {
    return this.balance;
  }

  @Override
  public double addBalance(double amount) {
    this.balance += amount;
    transactions.add(getAddBalanceTransaction(amount));
    return this.balance;
  }

  @Override
  public List<Transaction> getTransactions() {
    return transactions;
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

  private void reduceBalance(Double amount) {
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
}
