package apis;

import static spark.Spark.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import services.transactions.Transaction;
import services.transactions.TransactionException;
import services.transactions.TransactionService;

/**
 * This service is responsible for any operations related to money transactions.
 */
public class TransactionRestApi
{
    private static Logger logger = Logger.getLogger(TransactionRestApi.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        TransactionService transactionService = new TransactionService();;
        run(transactionService);
    }

    static void run(TransactionService transactionService) {
        // note that Spark was chosen mainly for the super quick setup, there are definitely better options out there
        // note that put would be more appropriate in this case, but decided to use get since it is quicker to call later
        get("/transaction/:sender/:receiver/:amount/:currency", (req,res)->{
            String from = req.params(":sender");
            String to = req.params(":receiver");
            String amount = req.params(":amount");
            String currency = req.params(":currency");

            return handleTransactionRequest(transactionService, from, to, amount, currency);
        });
    }

    /**
     * Handles request for making a money transaction from one account to another in a given currency.
     * @param transactionService the transaction service used to execute the transaction
     * @param sender the account money are taken from
     * @param receiver the account money are put in
     * @param amount the amount of money moved
     * @param currency the currency used during the execution of the transaction
     * @return true if transaction was successful, false otherwise
     */
    private static boolean handleTransactionRequest(TransactionService transactionService, String sender, String receiver, String amount, String currency) {
        Transaction transaction = new Transaction(sender, receiver, amount, currency);

        boolean transactionOutcome;
        try {
            transactionService.executeTransaction(transaction);
            transactionOutcome = true;
        } catch (TransactionException e) {
            logger.error("There was an error during the transaction execution", e);
            transactionOutcome = false;
        }

        return transactionOutcome;
    }
}