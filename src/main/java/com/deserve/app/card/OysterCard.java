package com.deserve.app.card;

import com.deserve.app.model.Journey;
import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;

import java.util.List;

public interface OysterCard {
  boolean swipeIn(Station station, JourneyType journeyType);

  boolean swipeOut(Station station);

  double getBalance();

  boolean addBalance(double amount);

  List<Journey> getTransactions();
}
