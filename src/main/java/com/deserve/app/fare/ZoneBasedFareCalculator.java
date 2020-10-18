package com.deserve.app.fare;

import com.deserve.app.model.Journey;
import com.deserve.app.rules.ZoneBasedFareRepository;

import java.util.Optional;

public class ZoneBasedFareCalculator implements FareCalculator {
  private ZoneBasedFareRepository fareRepository;

  public ZoneBasedFareCalculator(ZoneBasedFareRepository zoneBasedFareRepository) {
    fareRepository = zoneBasedFareRepository;
  }

  @Override
  public Optional<Double> getFare(Journey journey) {
    return Optional.empty();
  }
}
