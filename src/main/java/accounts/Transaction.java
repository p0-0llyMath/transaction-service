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
        this.amount = parseAmount(amount);
        this.currency = parseCurrency(currency);
    }

    private BigDecimal parseAmount(String amount) {

    }

    private Currency parseCurrency(String currency) {
        
    }
}
