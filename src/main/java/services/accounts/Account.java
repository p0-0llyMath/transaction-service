package services.accounts;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

/**
 * This class is responsible for storing account information.
 * Note that in production type of code, most likely this will be part of common library that gets imported across projects
 * rather than a stand-alone class at some service.
 */
public class Account {
    private String id;
    private BigDecimal amount;
    private Currency currency;

    public Account(String id, BigDecimal amount, Currency currency) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    /**
     * Adds the given amount to the account.
     * @param amount the amount to be added
     */
    void addAmount(BigDecimal amount) {
        // alternatively could have used AtomicReference but this is more clear imo, ditto for subtracting
        synchronized (this) {
            this.amount = this.amount.add(amount);
        }
    }

    /**
     * Subtracts the given amount from the account.
     * @param amount the amount to be subtracted
     */
    void subtractAmount(BigDecimal amount) {
        synchronized (this) {
            this.amount = this.amount.subtract(amount);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
