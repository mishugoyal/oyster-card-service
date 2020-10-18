package com.deserve.app.card;

import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;
import com.deserve.app.model.Transaction;

import java.util.List;

public interface OysterCard {
  boolean swipeIn(Station station, JourneyType journeyType);

  boolean swipeOut(Station station);

  double getBalance();

  double addBalance(double amount);

  List<Transaction> getTransactions();
}
