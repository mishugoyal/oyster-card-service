package com.deserve.app.card;

import com.deserve.app.fare.FareCalculator;
import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;
import com.deserve.app.model.Transaction;
import com.deserve.app.model.TransactionType;

import java.util.ArrayList;
import java.util.List;

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
}
