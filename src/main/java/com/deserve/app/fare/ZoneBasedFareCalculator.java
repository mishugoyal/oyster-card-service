package com.deserve.app.fare;

import com.deserve.app.model.Journey;

import java.util.Optional;

public class ZoneBasedFareCalculator implements FareCalculator {
  private final ZoneBasedFareRepository fareRepository;

  public ZoneBasedFareCalculator(ZoneBasedFareRepository zoneBasedFareRepository) {
    fareRepository = zoneBasedFareRepository;
  }

  @Override
  public Optional<Double> getFare(Journey journey) {
    if (!journey.getDestination().isPresent()) {
      return fareRepository.getDefaultFareForJourneyType(journey.getJourneyType());
    }

    double minFare = Double.MAX_VALUE;
    for (int sourceZone : journey.getSource().getZones()) {
      for (int destZone : journey.getDestination().get().getZones()) {
        Optional<Double> possibleFare =
            fareRepository.getFare(sourceZone, destZone, journey.getJourneyType());
        if (possibleFare.isPresent() && possibleFare.get() < minFare) {
          minFare = possibleFare.get();
        }
      }
    }
    if (minFare == Double.MAX_VALUE) {
      return Optional.empty();
    }
    return Optional.of(minFare);
  }
}
