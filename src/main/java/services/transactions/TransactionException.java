package services.transactions;

/**
 * Throw this exception when it is not possible to complete a transaction.
 */
public class TransactionException extends Exception {
    public TransactionException(String message) {
        super(message);
    }
}
