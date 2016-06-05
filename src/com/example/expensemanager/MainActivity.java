package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	Button addExpense, showexp,addIncome,showIncome,Chart,expenseManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addExpense = (Button) findViewById(R.id.button1);
		showexp = (Button) findViewById(R.id.button2);
		addIncome=(Button)findViewById(R.id.button3);
		showIncome=(Button)findViewById(R.id.button4);
		Chart=(Button)findViewById(R.id.button5);
		expenseManager=(Button)findViewById(R.id.button6);

		addExpense.setOnClickListener(this);
		showexp.setOnClickListener(this);
		addIncome.setOnClickListener(this);
		showIncome.setOnClickListener(this);
		Chart.setOnClickListener(this);
		expenseManager.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			startActivity(new Intent(MainActivity.this, AddExpense.class));
			break;
		case R.id.button2:
		startActivity(new Intent(MainActivity.this, ShowExpense.class));
		break;
		case R.id.button3:
			startActivity(new Intent(MainActivity.this,AddIncome.class));
			break;
		case R.id.button4:
			startActivity(new Intent(MainActivity.this,ShowIncome.class));
			break;
		case R.id.button5:
			startActivity(new Intent(MainActivity.this,Chart.class));
			break;
		case R.id.button6:
			startActivity(new Intent(MainActivity.this,Manager.class));
			break;
		}
	}
}
