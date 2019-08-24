package accounts;

import java.math.BigDecimal;

public interface Validator {
    boolean isUserValid(String user);
    boolean isAmountValid(BigDecimal amount);
}
