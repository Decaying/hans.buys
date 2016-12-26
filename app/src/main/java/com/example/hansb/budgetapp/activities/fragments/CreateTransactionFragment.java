package com.example.hansb.budgetapp.activities.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.hansb.budgetapp.AppInjector;
import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.budgetapp.TransactionRepository;
import com.example.hansb.budgetapp.business.Transaction;
import com.example.hansb.budgetapp.business.TransactionFactory;

import org.apache.logging.log4j.Logger;

/**
 * Created by HansB on 25/12/2016.
 */

public class CreateTransactionFragment
        extends Fragment
        implements Button.OnClickListener {
    private final Logger logger;
    private final TransactionRepository transactionRepository;
    private TransactionFactory transactionFactory;

    public CreateTransactionFragment() {
        super();

        throw new UnsupportedOperationException("we need an injector");
    }

    @SuppressLint("ValidFragment")
    public CreateTransactionFragment(AppInjector injector) {
        super();

        this.logger = injector.getLogger(CreateTransactionFragment.class);
        this.transactionRepository = injector.getTransactionRepository();
        this.transactionFactory = injector.getTransactionFactory();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        logger.debug("Creating transaction detail fragment view");
        return inflater.inflate(R.layout.transaction_detail_fragment,
                container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        logger.debug("transaction detail fragment view has been created");
        super.onActivityCreated(savedInstanceState);

        Button saveTransactionButton = getSaveTransactionButtonView();
        saveTransactionButton.setOnClickListener(this);
    }

    public Button getSaveTransactionButtonView() {
        return (Button) getActivity().findViewById(R.id.save_transaction);
    }

    @Override
    public void onClick(View v) {
        String description = getDescription();
        Double value = getValue();

        try {
            Transaction deposit = transactionFactory.createDeposit(description, value);
            transactionRepository.createTransaction(deposit);
        } catch (Exception ex) {
            logger.error("Failed to create transaction");
        }
    }

    public String getDescription() {
        return getTransactionDescriptionView().getText().toString();
    }

    public EditText getTransactionDescriptionView() {
        return (EditText) getActivity().findViewById(R.id.transaction_description);
    }

    public Double getValue() {
        String text = getTransactionValueView().getText().toString();

        return Double.parseDouble(text);
    }

    private EditText getTransactionValueView() {
        return (EditText) getActivity().findViewById(R.id.transaction_value);
    }
}
