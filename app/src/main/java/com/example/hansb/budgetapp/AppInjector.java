package com.example.hansb.budgetapp;

import android.content.Context;

import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.TransactionFactory;
import com.example.hansb.budgetapp.interactor.TransactionInteractor;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 23/12/2016.
 */
public interface AppInjector {
    TransactionRepository getTransactionRepository();

    Logger getLogger(Class<?> type);

    TransactionInteractor getTransactionInteractor();

    TransactionFactory getTransactionFactory();

    Context getContext();
}
