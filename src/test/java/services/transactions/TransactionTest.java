package services.transactions;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class TransactionTest {

    private String sender = "testSender";
    private String receiver = "testReceiver";
    private String amount = "300";
    private String currency = "GBP";

    private Transaction transaction;

    @Before
    public void runBeforeEach() {
        transaction = new Transaction(sender, receiver, amount, currency);
    }

    @Test
    public void testGetSender() {
        Assertions.assertThat(transaction.getSender()).isEqualTo(sender);
    }

    @Test
    public void testGetReceiver() {
        Assertions.assertThat(transaction.getReceiver()).isEqualTo(receiver);
    }

    @Test
    public void testGetAmount() {
        Assertions.assertThat(transaction.getAmount()).isEqualTo(new BigDecimal(amount));
    }

    @Test
    public void testGetCurrency() {
        Assertions.assertThat(transaction.getCurrency()).isEqualTo(Currency.getInstance(currency));
    }
}
