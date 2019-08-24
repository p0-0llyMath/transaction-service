package services.accounts;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Currency;

public class AccountServiceTest {

    private final String testId = "testId";

    @Before
    public void runBeforeEach() {
        AccountService.getInstance().clearAccountsData();
    }

    @Test
    public void testAddGetAccountWithNewAccount() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));

        AccountService.getInstance().addAccount(account);

        Account insertedAccount = AccountService.getInstance().getAccount(testId);
        Assertions.assertThat(insertedAccount).isEqualTo(account);
    }

    @Test
    public void testAddGetAccountWithOldAccount() {
        Account account1 = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));
        Account account2 = new Account(testId, new BigDecimal(600), Currency.getInstance("USD"));

        AccountService.getInstance().addAccount(account1);
        AccountService.getInstance().addAccount(account2);

        Account insertedAccount = AccountService.getInstance().getAccount(testId);
        Assertions.assertThat(insertedAccount).isEqualTo(account1);
    }

    @Test
    public void testIsAccountValidWhenValid() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));

        AccountService.getInstance().addAccount(account);

        Assertions.assertThat(AccountService.getInstance().isAccountValid(testId)).isEqualTo(true);
    }

    @Test
    public void testIsAccountValidWhenNotValid() {
        Assertions.assertThat(AccountService.getInstance().isAccountValid(testId)).isEqualTo(false);
    }

    @Test
    public void testAddToAccountWhenValid() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));
        AccountService.getInstance().addAccount(account);

        AccountService.getInstance().addToAccount(testId, new BigDecimal(200), Currency.getInstance("GBP"));
        BigDecimal newAmount = AccountService.getInstance().getAccount(testId).getAmount();
        BigDecimal expectedAmount = new BigDecimal(200).add(new BigDecimal(400));

        Assertions.assertThat(newAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void testAddToAccountWhenInvalid() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));
        AccountService.getInstance().addAccount(account);

        AccountService.getInstance().addToAccount(testId, new BigDecimal(-200), Currency.getInstance("GBP"));
        BigDecimal newAmount = AccountService.getInstance().getAccount(testId).getAmount();
        BigDecimal expectedAmount = new BigDecimal(400);

        Assertions.assertThat(newAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void testSubtractFromAccountWhenValid() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));
        AccountService.getInstance().addAccount(account);

        AccountService.getInstance().subtractFromAccount(testId, new BigDecimal(200), Currency.getInstance("GBP"));
        BigDecimal newAmount = AccountService.getInstance().getAccount(testId).getAmount();
        BigDecimal expectedAmount = new BigDecimal(400).subtract(new BigDecimal(200));

        Assertions.assertThat(newAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void testSubtractFromAccountWhenInvalid1() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));
        AccountService.getInstance().addAccount(account);

        AccountService.getInstance().subtractFromAccount(testId, new BigDecimal(-200), Currency.getInstance("GBP"));
        BigDecimal newAmount = AccountService.getInstance().getAccount(testId).getAmount();
        BigDecimal expectedAmount = new BigDecimal(400);

        Assertions.assertThat(newAmount).isEqualTo(expectedAmount);
    }

    @Test
    public void testSubtractFromAccountWhenInvalid2() {
        Account account = new Account(testId, new BigDecimal(400), Currency.getInstance("GBP"));
        AccountService.getInstance().addAccount(account);

        AccountService.getInstance().subtractFromAccount(testId, new BigDecimal(1000), Currency.getInstance("GBP"));
        BigDecimal newAmount = AccountService.getInstance().getAccount(testId).getAmount();
        BigDecimal expectedAmount = new BigDecimal(400);

        Assertions.assertThat(newAmount).isEqualTo(expectedAmount);
    }
}
