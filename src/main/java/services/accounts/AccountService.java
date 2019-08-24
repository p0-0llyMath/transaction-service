package services.accounts;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * This class is responsible for handling operations related to accounts.
 * Note that in production type of code this service can be a service on its own, with its own REST api.
 * Furthermore the AccountService can be broken down to AccountStorage and AccountOperations if the way accounts are
 * stored gets more complicated such as using database, etc.
 */
public class AccountService {
    private static Logger logger = Logger.getLogger(AccountService.class);
    private static AccountService instance;

    private Map<String, Account> accountIdToAccount;

    AccountService() {
        accountIdToAccount = new ConcurrentHashMap<>();
    }

    public static synchronized AccountService getInstance() {
        if (instance == null) {
            instance = new AccountService();
        }
        return instance;
    }

    /**
     * Clears any stored accounts.
     */
    public void clearAccountsData() {
        accountIdToAccount.clear();
    }

    /**
     * Adds an account for the given id if it doesn't exist already.
     * @param account the account to add
     */
    public void addAccount(Account account) {
        accountIdToAccount.putIfAbsent(account.getId(), account);
    }

    /**
     * Gets the account for the given id.
     * @param accountId the account id
     * @return the account or null if there is no account for this id
     */
    public Account getAccount(String accountId) {
        // could also throw an exception if the account id is missing
        return accountIdToAccount.get(accountId);
    }

    /**
     * Checks if an account is valid. An account is valid if it exists.
     * @param accountId the id of the account
     * @return true if the account is valid, false otherwise
     */
    public boolean isAccountValid(String accountId) {
        return accountIdToAccount.containsKey(accountId);
    }

    /**
     * Adds an amount to a given account.
     * @param accountId the id of the account to add the amount in
     * @param amount the amount to be added
     * @param currency the currency the amount is in
     */
    public void addToAccount(String accountId, BigDecimal amount, Currency currency) {
        Account account = accountIdToAccount.get(accountId);
        BigDecimal convertedAmount = convertAmount(amount, currency, account.getCurrency());

        if (isPositive(convertedAmount)) {
            account.addAmount(convertedAmount);
        } else {
            logger.warn("Could not add to account with id " + accountId + ". Amount " + amount + " is not positive.");
        }
    }

    /**
     * Subtracts an amount from a given account.
     * @param accountId the id of the account to add the amount in
     * @param amount the amount to be subtracted
     * @param currency the currency the amount is in
     */
    public void subtractFromAccount(String accountId, BigDecimal amount, Currency currency) {
        Account account = accountIdToAccount.get(accountId);
        BigDecimal convertedAmount = convertAmount(amount, currency, account.getCurrency());

        if (isSubtractionAllowed(account.getAmount(), convertedAmount)) {
            account.subtractAmount(convertedAmount);
        } else {
            account.getAmount();
            logger.warn("Could not subtract from account with id " + accountId + ". Amount " + amount + " was not positive or there were no sufficient funds.");
        }
    }

    private boolean isPositive(BigDecimal amount) {
        return amount.compareTo(new BigDecimal(0)) > 0;
    }

    private boolean isSubtractionAllowed(BigDecimal initialAmount, BigDecimal amountToSubtract) {
        return isPositive(amountToSubtract) && initialAmount.compareTo(amountToSubtract) >= 0;
    }

    private BigDecimal convertAmount(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        // note for simplicity there is no real implementation of the conversion between currencies and in this code
        // it assumed that the rate for all currencies is 1. If the system is to be extended to do the real stuff,
        // most likely there will be another service that handles conversions and has real time information for the
        // conversion rates
        logger.warn("In this universe all currencies are equal. Utopia!");
        BigDecimal convertedAmount = amount;
        return convertedAmount;
    }
}
