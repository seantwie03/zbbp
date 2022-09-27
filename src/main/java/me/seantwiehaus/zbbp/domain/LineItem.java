package me.seantwiehaus.zbbp.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Getter
@ToString
@SuppressWarnings("java:S107")
public class LineItem extends BaseDomain {
  private final Long id;
  private final BudgetMonth budgetMonth;
  private final String name;
  private final Money plannedAmount;
  private final Long categoryId;
  private final String description;
  /**
   * Unmodifiable List
   */
  private final List<Transaction> transactions;

  public LineItem(ItemType type,
                  BudgetMonth budgetMonth,
                  String name,
                  Double plannedAmount,
                  Long categoryId) {
    this(null, type, budgetMonth, name, new Money(plannedAmount), categoryId, null, null, null);
  }

  public LineItem(Long id,
                  ItemType type,
                  BudgetMonth budgetMonth,
                  String name,
                  Integer plannedAmount,
                  Long categoryId,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    this(id, type, budgetMonth, name, new Money(plannedAmount), categoryId, description, transactions, lastModifiedAt);
  }

  public LineItem(Long id,
                  ItemType type,
                  BudgetMonth budgetMonth,
                  String name,
                  Money plannedAmount,
                  Long categoryId,
                  String description,
                  List<Transaction> transactions,
                  Instant lastModifiedAt) {
    super(type, lastModifiedAt);
    this.id = id;
    this.budgetMonth = budgetMonth;
    this.name = name;
    this.plannedAmount = plannedAmount;
    this.categoryId = categoryId;
    this.description = description;
    this.transactions = transactions != null ? Collections.unmodifiableList(transactions) : List.of();
  }

  public Money calculateTotalTransactions() {
    return new Money(
        transactions
            .stream()
            .map(Transaction::getAmount)
            .mapToInt(Money::inCents)
            .sum());
  }

  public Double calculatePercentageTransacted() {
    return calculateTotalTransactions().inCents() * 100.0 / plannedAmount.inCents();
  }

  public Money calculateTotalRemaining() {
    return new Money(plannedAmount.inCents() - calculateTotalTransactions().inCents());
  }

  public Double calculatePercentageRemaining() {
    return calculateTotalRemaining().inCents() * 100.0 / plannedAmount.inCents();
  }
}
