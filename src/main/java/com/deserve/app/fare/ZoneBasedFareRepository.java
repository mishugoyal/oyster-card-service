package com.deserve.app.fare;

import com.deserve.app.model.JourneyType;

import java.util.*;

public class ZoneBasedFareRepository {
  Map<Map.Entry<Integer, Integer>, Double> tubeFareMap;
  List<Integer> zones;

  public ZoneBasedFareRepository() {
    tubeFareMap = new HashMap<>();
    zones = new ArrayList<>();
    initialize();
  }

  private void initialize() {
    zones = Arrays.asList(1, 2, 3);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(1, 1), 2.50);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(2, 2), 2.00);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(3, 3), 2.00);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(3, 1), 3.20);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(1, 3), 3.20);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(2, 3), 2.25);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(3, 2), 2.25);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(1, 2), 3.00);
    tubeFareMap.put(new AbstractMap.SimpleEntry<>(2, 1), 3.00);
  }

  public Optional<Double> getFare(int fromZone, int toZone, JourneyType journeyType) {
    if (!zones.contains(fromZone) || !zones.contains(toZone)) return Optional.empty();
    switch (journeyType) {
      case BUS:
        return getDefaultFareForJourneyType(JourneyType.BUS);
      case TUBE:
        return Optional.of(tubeFareMap.get(new AbstractMap.SimpleEntry<>(fromZone, toZone)));
      default:
        return Optional.empty();
    }
  }

  public Optional<Double> getDefaultFareForJourneyType(JourneyType journeyType) {
    switch (journeyType) {
      case TUBE:
        return Optional.of(3.20);
      case BUS:
        return Optional.of(1.80);
      default:
        return Optional.empty();
    }
  }
}
