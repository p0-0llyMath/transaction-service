package accounts;

import java.math.BigDecimal;

public class TransactionService {

    private AccountService accountService;
    private Validator validator;

    public TransactionService(Validator validator) {
        this.validator = validator;
    }

    public boolean validateTransaction(Transaction transaction) {
        boolean validTransactionParties = validator.isUserValid(transaction.getFrom()) && validator.isUserValid(transaction.getTo());
        boolean validAmount = validator.isAmountValid(transaction.getAmount());
        boolean validTransaction = validAmount && validTransactionParties;

        return validTransaction;
    }

    public TransactionStatus executeTransaction(Transaction transaction) {
        boolean validTransaction = validateTransaction(transaction);

        TransactionStatus transactionStatus;
        if (!validTransaction) {
            transactionStatus = new TransactionStatus(Status.ERROR, "Transaction fails: data is not valid.");
        } else {
            Account fromAccount = accountService.getAccount(transaction.getFrom(), transaction.getCurrency());
            Account toAccount = accountService.getAccount(transaction.getTo(), transaction.getCurrency());

            if (fromAccount == null) {
                transactionStatus = new TransactionStatus(Status.ERROR, "Transaction fails: user doesn't have account in the requested currency.");
            } else {
                if (toAccount == null) {
                    toAccount = accountService.getMainAccount(transaction.getTo());
                }

                if (fromAccount.getAmount() >= transaction.getAmount()) {
                    BigDecimal amountLeft = accountService.addToAccount(toAccount.getId(), transaction.getAmount(), transaction.getCurrency());
                    BigDecimal newAmount = accountService.subtractFromAccount(fromAccount.getId(), transaction.getAmount(), transaction.getCurrency());

                    transactionStatus = new TransactionStatus(Status.SUCCESS, amountLeft, newAmount);
                } else {
                    transactionStatus = new TransactionStatus(Status.ERROR, "Transaction fails: no sufficient funds to execute transaction.");
                }
            }
        }

        return transactionStatus;
    }

}
