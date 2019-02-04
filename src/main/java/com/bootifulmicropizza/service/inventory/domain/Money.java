package com.bootifulmicropizza.service.inventory.domain;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Basis class to represent monetary values.
 */
@Embeddable
public class Money implements Serializable {

    private BigDecimal amount;

    public Money() {

    }

    public Money(final double amount) {
        this.amount = setScale(new BigDecimal(amount));
    }

    public Money(final BigDecimal amount) {
        this.amount = setScale(amount);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private BigDecimal setScale(final BigDecimal value) {
        return value.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Money money = (Money) o;
        return Objects.equals(amount, money.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
