package com.deserve.app.card;

import com.deserve.app.fare.FareCalculator;
import com.deserve.app.model.Journey;
import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;

import java.util.List;

public class OysterCardInMemoryImpl implements OysterCard {
  private double balance;
  private final FareCalculator fareCalculator;

  public OysterCardInMemoryImpl(FareCalculator fareCalculator) {
    balance = 0;
    this.fareCalculator = fareCalculator;
  }

  public OysterCardInMemoryImpl(FareCalculator fareCalculator, double balance) {
    this.balance = balance;
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
    return 0;
  }

  @Override
  public boolean addBalance(double amount) {
    return false;
  }

  @Override
  public List<Journey> getTransactions() {
    return null;
  }
}
