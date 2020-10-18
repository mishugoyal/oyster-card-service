package com.deserve.app.card;

import com.deserve.app.fare.ZoneBasedFareCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
