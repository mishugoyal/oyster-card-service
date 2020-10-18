package com.deserve.app.fare;

import com.deserve.app.model.Journey;
import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;
import com.deserve.app.rules.ZoneBasedFareRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ZoneBasedFareCalculatorTest {
  @Mock ZoneBasedFareRepository zoneBasedFareRepository;
  ZoneBasedFareCalculator zoneBasedFareCalculator;

  @BeforeEach
  void setup() {
    zoneBasedFareCalculator = new ZoneBasedFareCalculator(zoneBasedFareRepository);
  }

  @Test
  void shouldReturnDefaultBusFareIfDestinationIsEmptyInJourney() {
    Journey journey = getTestJourneyWithNoDestination(JourneyType.BUS);
    when(zoneBasedFareRepository.getDefaultFareForJourneyType(JourneyType.BUS))
        .thenReturn(Optional.of(1.80));

    final Optional<Double> fare = zoneBasedFareCalculator.getFare(journey);

    assertEquals(Optional.of(1.80), fare);
  }

  @Test
  void shouldReturnDefaultTubeFareIfDestinationIsEmptyInJourney() {
    Journey journey = getTestJourneyWithNoDestination(JourneyType.TUBE);
    when(zoneBasedFareRepository.getDefaultFareForJourneyType(JourneyType.TUBE))
        .thenReturn(Optional.of(3.20));

    final Optional<Double> fare = zoneBasedFareCalculator.getFare(journey);

    assertEquals(Optional.of(3.20), fare);
  }

  @Test
  void shouldReturnMinFareIfMultipleFaresArePossible() {
    Journey journey = getTestJourneyWithDestination(JourneyType.TUBE);
    when(zoneBasedFareRepository.getFare(anyInt(), anyInt(), eq(JourneyType.TUBE)))
        .thenReturn(Optional.of(1.20))
        .thenReturn(Optional.of(2.25))
        .thenReturn(Optional.of(2.00))
        .thenReturn(Optional.of(2.10));

    final Optional<Double> fare = zoneBasedFareCalculator.getFare(journey);

    assertEquals(Optional.of(1.20), fare);
  }

  @Test
  void shouldReturnEmptyIfJourneyIsInvalid() {
    Journey journey = getInvalidJourney();

    assertEquals(Optional.empty(), zoneBasedFareCalculator.getFare(journey));
  }

  private Journey getInvalidJourney() {
    Journey journey = new Journey();
    journey.setJourneyType(JourneyType.BUS);
    journey.setSource(getStation("Holborn", Collections.singletonList(4)));
    journey.setDestination(getStation("Earl’s Court", Arrays.asList(2, 3)));
    return journey;
  }

  private Journey getTestJourneyWithDestination(JourneyType journeyType) {
    Journey journey = new Journey();
    journey.setJourneyType(journeyType);
    journey.setSource(getStation("Holborn", Arrays.asList(1, 2)));
    journey.setDestination(getStation("Earl’s Court", Arrays.asList(2, 3)));
    return journey;
  }

  private Journey getTestJourneyWithNoDestination(JourneyType journeyType) {
    Journey journey = new Journey();
    journey.setJourneyType(journeyType);
    journey.setSource(getStation("Holborn", Collections.singletonList(1)));
    return journey;
  }

  private Station getStation(String name, List<Integer> zones) {
    Station station = new Station();
    station.setName(name);
    station.setZones(zones);
    return station;
  }
}
