package services.transactions;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * This class represents a transaction.
 */
public class Transaction {

    private String sender;
    private String receiver;
    private BigDecimal amount;
    private Currency currency;

    public Transaction(String sender, String receiver, String amount, String currency) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = new BigDecimal(amount);
        this.currency = Currency.getInstance(currency);
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
