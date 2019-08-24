package services.transactions;

import services.accounts.Account;
import services.accounts.AccountService;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * This service is responsible for dealing with operations related to transactions.
 */
public class TransactionService {

    private static BigDecimal allowedMaxAmount;
    private AccountService accountService;

    TransactionService(BigDecimal allowedMaxAmount, AccountService accountService) {
        this.allowedMaxAmount = allowedMaxAmount;
        this.accountService = accountService;
    }

    public TransactionService() {
        allowedMaxAmount = new BigDecimal(500);
        accountService = AccountService.getInstance();
        accountService.addAccount(new Account("dummy1", new BigDecimal(400), Currency.getInstance("GBP")));
        accountService.addAccount(new Account("dummy2", new BigDecimal(10), Currency.getInstance("USD")));
    }

    /**
     * Executes a transaction.
     * @param transaction the transaction to execute
     * @throws TransactionException
     */
    public void executeTransaction(Transaction transaction) throws TransactionException {
        boolean validAmount = isAmountValid(transaction.getAmount());

        if (!validAmount) {
            throw new TransactionException("Can not complete transaction. Transaction is invalid.");
        }

        if (!accountService.isAccountValid(transaction.getSender()) || !accountService.isAccountValid(transaction.getReceiver())) {
            throw new TransactionException("Can not complete transaction. At least one of the accounts involved in the transactions is invalid.");
        }

        accountService.subtractFromAccount(transaction.getSender(), transaction.getAmount(), transaction.getCurrency());
        accountService.addToAccount(transaction.getReceiver(), transaction.getAmount(), transaction.getCurrency());
    }

    /**
     * Check if a the given amount is valid. A valid amount is positive and no more than the specified allowedMaxAmount.
     * @param amount the amount to validate
     * @return true if the amount is valid, false otherwise
     */
    private boolean isAmountValid(BigDecimal amount) {
        BigDecimal zeroAsBigDecimal = new BigDecimal(0);
        boolean positiveAmount = amount.compareTo(zeroAsBigDecimal) > 0;
        boolean allowedAmount = amount.compareTo(allowedMaxAmount) < 0;
        boolean validAmount = positiveAmount && allowedAmount;

        return validAmount;
    }
}
