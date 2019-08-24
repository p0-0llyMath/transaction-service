package accounts;

import java.math.BigDecimal;

public class GenericValidator implements Validator {

    private static final String BAD_PREFIX = "meow";
    private static final BigDecimal allowedMaxAmount = new BigDecimal(400);

    @Override
    public boolean isUserValid(String user) {
        // this is just a dummy validation; in reality, we could decide that validation happens before it reaches the
        // transaction service or we may validate a user against the database or have another service to get user information
        // such as does user exists, etc.
        boolean userWithBadPrefix = user.startsWith(BAD_PREFIX);
        boolean validUser = !userWithBadPrefix;

        return validUser;
    }

    @Override
    public boolean isAmountValid(BigDecimal amount) {
        BigDecimal zeroAsBigDecimal = new BigDecimal(0);
        boolean positiveAmount = amount.compareTo(zeroAsBigDecimal) > 0;
        boolean allowedAmount = amount.compareTo(allowedMaxAmount) < 0;
        boolean validAmount = positiveAmount && allowedAmount;

        return validAmount;
    }
}
