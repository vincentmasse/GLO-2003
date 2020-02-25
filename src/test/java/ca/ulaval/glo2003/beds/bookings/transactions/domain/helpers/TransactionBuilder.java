package ca.ulaval.glo2003.beds.bookings.transactions.domain.helpers;

import static ca.ulaval.glo2003.beds.bookings.transactions.domain.helpers.TransactionObjectMother.*;

import ca.ulaval.glo2003.beds.bookings.transactions.domain.Transaction;
import ca.ulaval.glo2003.beds.bookings.transactions.domain.TransactionReasons;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionBuilder {

  private TransactionBuilder() {}

  private LocalDate DEFAULT_TIMESTAMP = createTimestamp();
  private LocalDate timestamp = DEFAULT_TIMESTAMP;

  private String DEFAULT_FROM = createFrom();
  private String from = DEFAULT_FROM;

  private String DEFAULT_TO = createTo();
  private String to = DEFAULT_TO;

  private BigDecimal DEFAULT_TOTAL = createTotal();
  private BigDecimal total = DEFAULT_TOTAL;

  private TransactionReasons DEFAULT_REASON = createReason();
  private TransactionReasons reason = DEFAULT_REASON;

  public static TransactionBuilder aTransaction() {
    return new TransactionBuilder();
  }

  public Transaction build() {
    return new Transaction(timestamp, from, to, total, reason);
  }
}
