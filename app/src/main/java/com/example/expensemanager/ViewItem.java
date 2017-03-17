package com.example.expensemanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewItem extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Intent intent = getIntent();
        boolean isIncome = intent.getBooleanExtra("isIncome", false);
        String id = intent.getStringExtra("id");



    }
}
