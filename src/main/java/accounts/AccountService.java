package accounts;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountService {
    // needs to be singleton
    private Map<String, List<Account>> userToAccounts;

    public AccountService() {
        userToAccounts = new HashMap<>();
    }

    public void addAcount(String user, Currency currency, BigDecimal startingAmount) {

    }

    public Account getAccount(String from, Currency currency) {
    }

    public Account getMainAccount(String to) {

    }

    public BigDecimal addToAccount(Object id, BigDecimal amount, Currency currency) {
    }

    public BigDecimal subtractFromAccount(Object id, BigDecimal amount, Currency currency) {
    }
}
