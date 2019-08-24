package services.transactions;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TransactionExceptionTest {
    @Test
    public void testTransactionException() {
        String message = "some test message";
        TransactionException transactionException = new TransactionException(message);

        Assertions.assertThat(transactionException.getMessage()).isEqualTo(message);
    }
}
