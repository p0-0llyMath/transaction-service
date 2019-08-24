package apis;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import services.transactions.Transaction;
import services.transactions.TransactionException;
import services.transactions.TransactionService;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


import static spark.Spark.awaitInitialization;
import static spark.Spark.stop;

@RunWith(MockitoJUnitRunner.class)
public class TransactionRestApiTest {
    @Mock
    private TransactionService transactionService;

    @Before
    public void runBeforeEach() {
        TransactionRestApi.run(transactionService);
        awaitInitialization();
    }

    @After
    public void runAfterEach() throws InterruptedException {
        stop();
        Thread.sleep(300);
    }

    @Test
    public void testTransactionRequestWithSuccess() throws IOException {
        String testUrl = "/transaction/some_sender/some_receiver/200/GBP";
        URL requestUrl = new URL("http://localhost:4567" + testUrl);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        String body = IOUtils.toString(connection.getInputStream());

        Assertions.assertThat(body).isEqualTo("true");
    }

    @Test
    public void testTransactionRequestWithFailure() throws IOException, TransactionException {
        Mockito.doThrow(new TransactionException("some message")).when(transactionService).executeTransaction(Mockito.any(Transaction.class));

        String testUrl = "/transaction/some_sender/some_receiver/200/GBP";
        URL requestUrl = new URL("http://localhost:4567" + testUrl);
        HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoOutput(true);
        connection.connect();
        String body = IOUtils.toString(connection.getInputStream());

        Assertions.assertThat(body).isEqualTo("false");
    }
}
