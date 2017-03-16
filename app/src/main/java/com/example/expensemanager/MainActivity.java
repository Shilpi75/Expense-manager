package com.example.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button mAddIncome, mAddExpense, mShowIncome, mShowExpense, mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddIncome = (Button)findViewById(R.id.bt_add_income);
        mAddExpense = (Button)findViewById(R.id.bt_add_expense);
        mShowIncome = (Button)findViewById(R.id.bt_show_income);
        mShowExpense = (Button)findViewById(R.id.bt_show_expense);
        mManager = (Button)findViewById(R.id.bt_manager);

        mAddIncome.setOnClickListener(this);
        mAddExpense.setOnClickListener(this);
        mShowIncome.setOnClickListener(this);
        mShowExpense.setOnClickListener(this);
        mManager.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.bt_add_expense:
                startActivity(new Intent(this, AddExpenseActivity.class));
                break;
            case R.id.bt_add_income :
                startActivity(new Intent(this, AddIncomeActivity.class));
                break;
            case R.id.bt_show_expense :
                break;
            case R.id.bt_show_income :
                startActivity(new Intent(this, ShowIncomeActivity.class));
                break;
            case R.id.bt_manager:
                startActivity(new Intent(this, ManagerActivity.class));
                break;
        }
    }
}
