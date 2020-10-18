package com.deserve.app;

import com.deserve.app.card.OysterCard;
import com.deserve.app.card.OysterCardInMemoryImpl;
import com.deserve.app.fare.FareCalculator;
import com.deserve.app.fare.ZoneBasedFareCalculator;
import com.deserve.app.fare.ZoneBasedFareRepository;
import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {
  private static final String EARLS_COURT_STATION_NAME = "Earl’s Court";
  private static final String HOLBORN_STATION_NAME = "Holborn";
  private static final String HAMMERSMITH_STATION_NAME = "Hammersmith";
  private static final String CHELSEA_STATION_NAME = "Chelsea";

  public static void main(String[] args) {
    ZoneBasedFareRepository fareRepository = new ZoneBasedFareRepository();
    FareCalculator fareCalculator = new ZoneBasedFareCalculator(fareRepository);
    OysterCard oysterCard = new OysterCardInMemoryImpl(fareCalculator);
    oysterCard.addBalance(30);

    oysterCard.swipeIn(getStation(HOLBORN_STATION_NAME, Collections.singletonList(1)), JourneyType.TUBE);
    oysterCard.swipeOut(getStation(EARLS_COURT_STATION_NAME, Arrays.asList(1, 2)));
    oysterCard.swipeIn(getStation(EARLS_COURT_STATION_NAME, Arrays.asList(1, 2)), JourneyType.BUS);
    oysterCard.swipeOut(getStation(CHELSEA_STATION_NAME, Collections.singletonList(4)));
    oysterCard.swipeIn(getStation(EARLS_COURT_STATION_NAME, Arrays.asList(1, 2)), JourneyType.TUBE);
    oysterCard.swipeOut(getStation(HAMMERSMITH_STATION_NAME, Collections.singletonList(2)));

    System.out.println("Current balance: " + oysterCard.getBalance());
    System.out.println("Transactions on card: " + oysterCard.getTransactions());
  }

  private static Station getStation(String name, List<Integer> zones) {
    Station station = new Station();
    station.setName(name);
    station.setZones(zones);

    return station;
  }
}
