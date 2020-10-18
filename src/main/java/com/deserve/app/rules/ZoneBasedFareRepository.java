package com.deserve.app.rules;

import com.deserve.app.model.JourneyType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ZoneBasedFareRepository {
  Map<String, Double> fareRuleMap;

  public ZoneBasedFareRepository() {
    fareRuleMap = new HashMap<>();
    initializeMap();
  }

  private void initializeMap() {}

  public Optional<Double> getFare(int fromZone, int toZone, JourneyType journeyType) {
    return Optional.empty();
  }

  public double getDefaultFareForJourneyType(JourneyType journeyType) {
    if (JourneyType.BUS == journeyType) {
      return 1.80;
    }
    return 3.20;
  }
}
