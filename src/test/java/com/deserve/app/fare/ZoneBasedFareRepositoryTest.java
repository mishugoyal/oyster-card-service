package com.deserve.app.fare;

import com.deserve.app.fare.ZoneBasedFareRepository;
import com.deserve.app.model.JourneyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

class ZoneBasedFareRepositoryTest {
  ZoneBasedFareRepository zoneBasedFareRepository;

  @BeforeEach
  void setup() {
    zoneBasedFareRepository = new ZoneBasedFareRepository();
  }

  @ParameterizedTest(name = "{index} => fromZone={0}, toZone={1}")
  @CsvSource({"1, 2", "1, 1", "2, 3", "3, 3", "3,1"})
  void shouldReturnFlatFareForBusTypeJourney(int fromZone, int toZone) {
    assertEquals(
        Optional.of(1.80), zoneBasedFareRepository.getFare(fromZone, toZone, JourneyType.BUS));
  }

  @Test
  void shouldReturnFareForTubeJourneyInSameZoneOne() {
    assertEquals(Optional.of(2.50), zoneBasedFareRepository.getFare(1, 1, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyInSameZoneTwo() {
    assertEquals(Optional.of(2.00), zoneBasedFareRepository.getFare(2, 2, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyInSameZoneThree() {
    assertEquals(Optional.of(2.00), zoneBasedFareRepository.getFare(3, 3, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyFromZoneOneToTwo() {
    assertEquals(Optional.of(3.00), zoneBasedFareRepository.getFare(1, 2, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyFromZoneTwoToOne() {
    assertEquals(Optional.of(3.00), zoneBasedFareRepository.getFare(2, 1, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyFromZoneTwoToThree() {
    assertEquals(Optional.of(2.25), zoneBasedFareRepository.getFare(2, 3, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyFromZoneThreeToTwo() {
    assertEquals(Optional.of(2.25), zoneBasedFareRepository.getFare(3, 2, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyFromZoneThreeToOne() {
    assertEquals(Optional.of(3.20), zoneBasedFareRepository.getFare(3, 1, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFareForTubeJourneyFromZoneOneToThree() {
    assertEquals(Optional.of(3.20), zoneBasedFareRepository.getFare(1, 3, JourneyType.TUBE));
  }

  @Test
  void shouldReturnEmptyIfEitherOfTheZonesIsNotFoundInRepository() {
    assertEquals(Optional.empty(), zoneBasedFareRepository.getFare(1, 4, JourneyType.TUBE));
  }

  @Test
  void shouldReturnDefaultFareForBusJourneyType() {
    assertEquals(
        Optional.of(1.80), zoneBasedFareRepository.getDefaultFareForJourneyType(JourneyType.BUS));
  }

  @Test
  void shouldReturnDefaultFareForTubeJourneyType() {
    assertEquals(
        Optional.of(3.20), zoneBasedFareRepository.getDefaultFareForJourneyType(JourneyType.TUBE));
  }
}
