package services.transactions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import services.accounts.AccountService;

import java.math.BigDecimal;
import java.util.Currency;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {
    private static final BigDecimal allowedMaxAmount = new BigDecimal(300);

    @Mock
    private AccountService accountServiceMock;

    private TransactionService transactionService;

    @Before
    public void runBeforeEach() {
        transactionService = new TransactionService(allowedMaxAmount, accountServiceMock);
    }

    @Test
    public void testExecuteTransactionWhenValid() throws TransactionException {
        Transaction transaction = new Transaction("sender", "receiver", "100", "GBP");
        Mockito.when(accountServiceMock.isAccountValid(Mockito.anyString())).thenReturn(true);

        transactionService.executeTransaction(transaction);

        Mockito.verify(accountServiceMock).subtractFromAccount("sender", new BigDecimal("100"), Currency.getInstance("GBP"));
        Mockito.verify(accountServiceMock).addToAccount("receiver", new BigDecimal("100"), Currency.getInstance("GBP"));
    }

    @Test(expected=TransactionException.class)
    public void testExecuteTransactionWhenInvalid1() throws TransactionException {
        Transaction transaction = new Transaction("sender", "receiver", "-100", "GBP");

        transactionService.executeTransaction(transaction);

        Mockito.verify(accountServiceMock, Mockito.never()).subtractFromAccount(Mockito.anyString(), Mockito.any(BigDecimal.class), Mockito.any(Currency.class));
        Mockito.verify(accountServiceMock, Mockito.never()).addToAccount(Mockito.anyString(), Mockito.any(BigDecimal.class), Mockito.any(Currency.class));
    }

    @Test(expected=TransactionException.class)
    public void testExecuteTransactionWhenInvalid2() throws TransactionException {
        Transaction transaction = new Transaction("sender", "receiver", "500", "GBP");

        transactionService.executeTransaction(transaction);

        Mockito.verify(accountServiceMock, Mockito.never()).subtractFromAccount(Mockito.anyString(), Mockito.any(BigDecimal.class), Mockito.any(Currency.class));
        Mockito.verify(accountServiceMock, Mockito.never()).addToAccount(Mockito.anyString(), Mockito.any(BigDecimal.class), Mockito.any(Currency.class));
    }

    @Test(expected=TransactionException.class)
    public void testExecuteTransactionWhenInvalid3() throws TransactionException {
        Transaction transaction = new Transaction("sender", "receiver", "500", "GBP");
        Mockito.when(accountServiceMock.isAccountValid(Mockito.anyString())).thenReturn(false);

        transactionService.executeTransaction(transaction);

        Mockito.verify(accountServiceMock, Mockito.never()).subtractFromAccount(Mockito.anyString(), Mockito.any(BigDecimal.class), Mockito.any(Currency.class));
        Mockito.verify(accountServiceMock, Mockito.never()).addToAccount(Mockito.anyString(), Mockito.any(BigDecimal.class), Mockito.any(Currency.class));
    }

}
