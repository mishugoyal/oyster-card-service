package com.deserve.app.rules;

import com.deserve.app.model.JourneyType;

import java.util.Optional;

public class ZoneBasedFareRepository {

  public Optional<Double> getFare(int fromZone, int toZone, JourneyType journeyType) {
    return Optional.empty();
  }
}
