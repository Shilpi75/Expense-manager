package com.example.expensemanager;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.Calendar;

public class ManagerActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView mIncomeDetails, mExpenseDetails, mBalance, mMonth;
    FloatingActionMenu fabMenu;
    FloatingActionButton fabIncome, fabExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        mIncomeDetails = (TextView)findViewById(R.id.tv_income_details);
        mExpenseDetails = (TextView)findViewById(R.id.tv_expense_details);
        mBalance = (TextView)findViewById(R.id.tv_balance);
        mMonth = (TextView)findViewById(R.id.tv_month);
        fabMenu = (FloatingActionMenu)findViewById(R.id.fab_menu);
        fabIncome = (FloatingActionButton)findViewById(R.id.fab_item_income);
        fabExpense = (FloatingActionButton)findViewById(R.id.fab_item_expense);
        setSupportActionBar(toolbar);

        String[] monthNames = { "January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November",
                "December" };
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        mMonth.setText(monthNames[month] + " " + year);

        DatabaseHandler handler = new DatabaseHandler(this);
        double expense = handler.MonthlyExpense(month + 1, year);
        double income = handler.MonthlyIncome(month + 1, year);

        mBalance.setTextColor(getResources().getColor(android.R.color.black));
        mBalance.setText("Balance: ");
        DecimalFormat df = new DecimalFormat("####0.00");
        double balance = income - expense;
        if (balance <= 0)
            mBalance.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        else
            mBalance.setTextColor(Color.parseColor("#006600"));
        mIncomeDetails.setText("Income\n" + df.format(income));
       mExpenseDetails.append("Expense\n" + df.format(expense));

        mBalance.append("" + df.format(balance));


        fabIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerActivity.this, AddIncomeActivity.class));
            }
        });

        fabExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerActivity.this, AddExpenseActivity.class));
            }
        });


    }
}
