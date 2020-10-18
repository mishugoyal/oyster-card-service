package com.deserve.app.card;

import com.deserve.app.fare.ZoneBasedFareCalculator;
import com.deserve.app.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.deserve.app.model.TransactionType.CREDIT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
}
