package com.example.hansb.budgetapp.repository;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.SqlTransactionFactory;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.services.TimeService;
import com.noveogroup.android.log.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HansB on 9/12/2016.
 */
public class FakeTransactionRepository implements TransactionRepository {
    private final TransactionFactory transactionFactory;
    private final SqlTransactionFactory sqlTransactionFactory;
    private final Logger logger;
    private final TimeService timeService;

    private List<Transaction> transactions = new ArrayList<>();
    private boolean shouldThrowException = false;
    private boolean getAllTransactionsHasBeenCalled = false;
    private boolean aNewTransactionHasBeenCreated = false;
    private Transaction newlyCreatedTransaction = null;

    public FakeTransactionRepository(AppInjector injector) {
        this.transactionFactory = injector.getTransactionFactory();
        sqlTransactionFactory = (SqlTransactionFactory) injector.getTransactionFactory();
        this.logger = injector.getLogger(FakeTransactionRepository.class);
        this.timeService = injector.getTimeService();
    }

    @Override
    public Transaction[] getAllTransactions() throws Exception {
        if (shouldThrowException) {
            logger.d("failing for test");
            throw new Exception("Had to fail for test");
        }
        logger.d("Fake transaction repository has been called");
        getAllTransactionsHasBeenCalled = true;
        Transaction[] array = new Transaction[transactions.size()];
        return transactions.toArray(array);
    }

    @Override
    public Transaction saveTransaction(Transaction transaction) {
        aNewTransactionHasBeenCreated = true;
        newlyCreatedTransaction = transaction;
        return newlyCreatedTransaction;
    }

    @Override
    public void setConversionRateFor(Long transactionId, Double conversionRate) {
        logger.e("not yet implemented");
    }

    public void whenOneDepositTransactionIsAvailable(double value, String description) throws Exception {
        Date now = timeService.now();

        logger.d(String.format("One deposit should be available"));

        transactions.add(transactionFactory.create(TransactionFactory.TransactionType.Deposit, description, value, "EUR", now));
    }

    public void whenOneDepositTransactionIsAvailable(double value, String description, String currency) throws Exception {
        Date now = timeService.now();

        logger.d(String.format("One deposit should be available"));

        transactions.add(transactionFactory.create(TransactionFactory.TransactionType.Deposit, description, value, currency, now));
    }

    public void whenOneDepositTransactionIsAvailable(double value, String description, String currency, Double conversionRate) throws Exception {
        Date now = timeService.now();

        logger.d(String.format("One deposit should be available"));

        Transaction transaction = sqlTransactionFactory.createFromSql(sqlTransactionFactory.getSqlTypeDeposit(), 0, description, value, currency, now, conversionRate);

        transactions.add(transaction);
    }

    public void whenOneDepositTransactionIsAvailable() throws Exception {
        whenOneDepositTransactionIsAvailable(1.00, "test description", "EUR");
    }

    public void whenDbUnavailable() {
        shouldThrowException = true;
    }

    public boolean getAllTransactionsHasBeenCalled() {
        return getAllTransactionsHasBeenCalled;
    }

    public boolean hasANewTransactionHasBeenCreated() {
        return aNewTransactionHasBeenCreated;
    }

    public Transaction newlyCreatedTransaction() {
        return newlyCreatedTransaction;
    }
}
