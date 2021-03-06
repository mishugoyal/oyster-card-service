package com.deserve.app.card;

import com.deserve.app.fare.ZoneBasedFareCalculator;
import com.deserve.app.model.Journey;
import com.deserve.app.model.JourneyType;
import com.deserve.app.model.Station;
import com.deserve.app.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.deserve.app.model.TransactionType.CREDIT;
import static com.deserve.app.model.TransactionType.DEBIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OysterCardInMemoryImplTest {
  @Mock ZoneBasedFareCalculator zoneBasedFareCalculator;
  OysterCardInMemoryImpl oysterCard;

  @BeforeEach
  void setup() {
    oysterCard = new OysterCardInMemoryImpl(zoneBasedFareCalculator);
  }

  @Test
  void shouldAddBalanceToCardWhenAddBalanceIsInvoked() {
    assertEquals(30, oysterCard.addBalance(30));
  }

  @Test
  void shouldReturnBalanceWhenGetBalanceIsInvoked() {
    assertEquals(0, oysterCard.getBalance());
  }

  @Test
  void shouldAddTheTransactionInCardWhenAddBalanceIsInvoked() {
    oysterCard.addBalance(10);

    List<Transaction> transactionList = oysterCard.getTransactions();

    assertEquals(10, transactionList.get(0).getAmount());
    assertEquals(CREDIT, transactionList.get(0).getTransactionType());
    assertFalse(transactionList.get(0).getJourney().isPresent());
  }

  @Test
  void shouldReturnTrueForValidSwipeIn() {
    Station station = getStation("Chelsea", Collections.singletonList(1));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.of(3.20));
    oysterCard.addBalance(10);

    assertTrue(oysterCard.swipeIn(station, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFalseForInvalidSwipeIn() {
    Station station = getStation("SomeStation", Collections.singletonList(4));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.empty());
    oysterCard.addBalance(10);

    assertFalse(oysterCard.swipeIn(station, JourneyType.TUBE));
  }

  @Test
  void shouldReturnFalseIfCardHasBalanceLessThanDefaultMaxFare() {
    Station station = getStation("Chelsea", Collections.singletonList(1));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.of(3.20));

    assertFalse(oysterCard.swipeIn(station, JourneyType.TUBE));
  }

  @Test
  void shouldDebitDefaultMaxFareWhenSwipeInIsInvoked() {
    Station station = getStation("Chelsea", Collections.singletonList(1));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.of(3.20));
    oysterCard.addBalance(10);

    oysterCard.swipeIn(station, JourneyType.TUBE);

    assertEquals(6.80, oysterCard.getBalance());
  }

  @Test
  void shouldAddDefaultDebitTransactionWithMaxFareWhenSwipeInIsInvoked() {
    Station station = getStation("Chelsea", Collections.singletonList(1));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.of(3.20));
    oysterCard.addBalance(10);

    oysterCard.swipeIn(station, JourneyType.TUBE);

    final List<Transaction> transactions = oysterCard.getTransactions();
    final Transaction lastTransaction = transactions.get(transactions.size() - 1);
    assertEquals(3.20, lastTransaction.getAmount());
    assertEquals(DEBIT, lastTransaction.getTransactionType());
    assertEquals("Chelsea", lastTransaction.getJourney().get().getSource().getName());
    assertEquals(Optional.empty(), lastTransaction.getJourney().get().getDestination());
    assertEquals(JourneyType.TUBE, lastTransaction.getJourney().get().getJourneyType());
  }

  @Test
  void shouldReturnFalseWhenSwipeOutIsInvokedWithoutSwipeInFirst() {
    Station station = getStation("Holborn", Arrays.asList(1, 2));

    assertFalse(oysterCard.swipeOut(station));
  }

  @Test
  void shouldReturnFalseWhenSwipeOutIsInvokedWithInvalidStation() {
    Station source = getStation("Holborn", Arrays.asList(1, 2));
    Station destination = getStation("SomeStation", Collections.singletonList(4));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.of(3.20)).thenReturn(Optional.empty());
    oysterCard.addBalance(10);
    oysterCard.swipeIn(source, JourneyType.TUBE);

    assertFalse(oysterCard.swipeOut(destination));
  }

  @Test
  void shouldReturnTrueWhenSwipeOutIsInvokedAfterSwipeIn() {
    Station source = getStation("Holborn", Arrays.asList(1, 2));
    Station destination = getStation("Wimbledon", Collections.singletonList(3));
    when(zoneBasedFareCalculator.getFare(any(Journey.class))).thenReturn(Optional.of(3.20));
    oysterCard.addBalance(10);
    oysterCard.swipeIn(source, JourneyType.TUBE);

    assertTrue(oysterCard.swipeOut(destination));
  }

  @Test
  void shouldUpdateTheBalanceAndDestinationOfLastTransactionWhenSwipeOutIsInvoked() {
    Station source = getStation("Holborn", Arrays.asList(1, 2));
    Station destination = getStation("Wimbledon", Collections.singletonList(3));
    when(zoneBasedFareCalculator.getFare(any(Journey.class)))
        .thenReturn(Optional.of(3.20))
        .thenReturn(Optional.of(2.20));
    oysterCard.addBalance(10);
    oysterCard.swipeIn(source, JourneyType.TUBE);

    final List<Transaction> transactions = oysterCard.getTransactions();
    final Transaction lastTransaction = transactions.get(transactions.size() - 1);
    assertEquals(3.20, lastTransaction.getAmount());
    assertEquals(Optional.empty(), lastTransaction.getJourney().get().getDestination());

    oysterCard.swipeOut(destination);

    assertEquals(2.20, lastTransaction.getAmount());
    assertEquals("Holborn", lastTransaction.getJourney().get().getSource().getName());
    assertEquals("Wimbledon", lastTransaction.getJourney().get().getDestination().get().getName());
    assertEquals(JourneyType.TUBE, lastTransaction.getJourney().get().getJourneyType());
  }

  @Test
  void shouldUpdateCurrBalanceWhenSwipeOutIsInvoked() {
    Station source = getStation("Holborn", Arrays.asList(1, 2));
    Station destination = getStation("Wimbledon", Collections.singletonList(3));
    when(zoneBasedFareCalculator.getFare(any(Journey.class)))
        .thenReturn(Optional.of(3.20))
        .thenReturn(Optional.of(2.20));
    oysterCard.addBalance(10);
    oysterCard.swipeIn(source, JourneyType.TUBE);

    assertEquals(6.80, oysterCard.getBalance());

    oysterCard.swipeOut(destination);

    assertEquals(7.80, oysterCard.getBalance());
  }

  private Station getStation(String name, List<Integer> zones) {
    Station station = new Station();
    station.setName(name);
    station.setZones(zones);
    return station;
  }
}
