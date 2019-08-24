package accounts;

import static spark.Spark.*;

/**
 * Hello world!
 *
 */
public class App 
{

    public static void main(String[] args) {
        get("/transfer/:from/:to/:amount/:currency", (req,res)->{
            String from = req.params(":from");
            String to = req.params(":to");
            String amount = req.params(":amount");
            String currency = req.params(":currency");

            Transaction transaction = new Transaction(from, to, amount, currency);
            TransactionService transactionService = new TransactionService();
            TransactionStatus transactionStatus = transactionService.executeTransaction(transaction);

            return transactionStatus;
        });
    }
}