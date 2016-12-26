package com.example.hansb.budgetapp.activities;

import android.app.Instrumentation;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hansb.budgetapp.R;
import com.example.hansb.budgetapp.TestAppInjector;
import com.example.hansb.budgetapp.repository.FakeTransactionRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Created by HansB on 25/12/2016.
 */
public class MainActivityTestBase extends ActivityTestBase<MainActivity> {
    protected FakeTransactionRepository FakeTransactionRepository;

    public MainActivityTestBase() {
        super(MainActivity.class);
    }

    @Override
    public void configureContainer(TestAppInjector injector) {
        super.configureContainer(injector);

        MainActivity.Injector = injector;

        FakeTransactionRepository = new FakeTransactionRepository(injector);
        injector.setTransactionRepository(FakeTransactionRepository);
    }

    protected ListView getTransactionListView(MainActivity activity) {
        return getView(activity, R.id.transactionlist);
    }

    protected View getTransactionItemView(MainActivity activity, int position) {
        View transactionView = getTransactionListView(activity).getChildAt(position);

        assertThat(transactionView, is(notNullValue()));

        return transactionView;
    }

    protected Instrumentation.ActivityMonitor setupActivityMonitor() {
        return getInstrumentation().addMonitor(TransactionDetailActivity.class.getName(), null, false);
    }

    protected void clickAddTransactionButton(MainActivity activity) {
        clickView(getAddTransactionButton(activity));
    }

    private FloatingActionButton getAddTransactionButton(MainActivity activity) {
        return getView(activity, R.id.add_transaction);
    }

    protected void waitForTransactionDetailActivity(Instrumentation.ActivityMonitor activityMonitor) {
        TransactionDetailActivity nextActivity = (TransactionDetailActivity) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

        assertNotNull(nextActivity);

        nextActivity.finish();
    }

    protected TextView getDetailLine(View transactionView) {
        return (TextView) transactionView.findViewById(R.id.detailLine);
    }

    protected TextView getHeaderLine(View transactionView) {
        return (TextView) transactionView.findViewById(R.id.headerLine);
    }
}
