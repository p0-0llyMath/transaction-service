package accounts;

import java.math.BigDecimal;
import java.util.Currency;

public class Account {
    private String id;
    private BigDecimal amount;
    private Currency currency;

    public Account(String id, BigDecimal amount, Currency currency) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getId() {
        return id;
    }
}
