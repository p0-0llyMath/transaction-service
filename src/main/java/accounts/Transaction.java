package accounts;

import java.math.BigDecimal;
import java.util.Currency;

public class Transaction {

    private String from;
    private String to;
    private BigDecimal amount;
    private Currency currency;

    public Transaction(String from, String to, String amount, String currency) {
        this.from = from;
        this.to = to;
        this.amount = new BigDecimal(amount);
        this.currency = Currency.getInstance(currency);
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
