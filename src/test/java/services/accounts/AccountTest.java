package services.accounts;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class AccountTest {

    private Account account;
    private String id = "testId";
    private BigDecimal amount = new BigDecimal(500);
    private Currency currency = Currency.getInstance("GBP");

    @Before
    public void runBeforeEach() {
        account = new Account(id, amount, currency);
    }

    @Test
    public void testGetId() {
        Assertions.assertThat(account.getId()).isEqualTo(id);
    }

    @Test
    public void testGetAmount() {
        Assertions.assertThat(account.getAmount()).isEqualTo(amount);
    }

    @Test
    public void testGetCurrency() {
        Assertions.assertThat(account.getCurrency()).isEqualTo(currency);
    }

    @Test
    public void testAddAmount() {
        BigDecimal amountToAdd = new BigDecimal(300);
        BigDecimal expectedAmount = amountToAdd.add(amount);

        account.addAmount(amountToAdd);

        Assertions.assertThat(account.getAmount()).isEqualTo(expectedAmount);
    }

    @Test
    public void testSubtractAmount() {
        BigDecimal amountToSubtract = new BigDecimal(300);
        BigDecimal expectedAmount = amount.subtract(amountToSubtract);

        account.subtractAmount(amountToSubtract);

        Assertions.assertThat(account.getAmount()).isEqualTo(expectedAmount);
    }
}
