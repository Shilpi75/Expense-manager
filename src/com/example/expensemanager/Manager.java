package com.example.expensemanager;

import java.text.DecimalFormat;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Manager extends AppCompatActivity implements OnClickListener, OnItemClickListener {

	Toolbar mtoolbar;
	TextView curMonth, incomeText, expenseText, balanceText;
	DatabaseHandler handler;
	ImageButton incomeButton, expenseButton, add;
	DrawerLayout drawerLayout;
	ActionBarDrawerToggle actionBarDrawerToogle;
	ListView navlist;
	FragmentManager fragmanager;
	FragmentTransaction fragTransaction;
	boolean doubleBackToExitPressedOnce=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager);

		mtoolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
		curMonth = (TextView) findViewById(R.id.textView1);
		incomeText = (TextView) findViewById(R.id.textView2);
		expenseText = (TextView) findViewById(R.id.textView3);
		balanceText = (TextView) findViewById(R.id.textbalance);
		incomeButton = (ImageButton) findViewById(R.id.imageButton1);
		expenseButton = (ImageButton) findViewById(R.id.imageButton2);
		add = (ImageButton) findViewById(R.id.add);
		drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		navlist = (ListView) findViewById(R.id.navlist);

		mtoolbar.setTitle("Expense Manager");
		setSupportActionBar(mtoolbar);
		// getSupportActionBar().setIcon(R.drawable.ic_launcher);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		actionBarDrawerToogle = new ActionBarDrawerToggle(this, drawerLayout,
				R.string.open, R.string.close);
		drawerLayout.setDrawerListener(actionBarDrawerToogle);

		String[] monthNames = { "January", "February", "March", "April", "May",
				"June", "July", "August", "September", "October", "November",
				"December" };
		Calendar calendar = Calendar.getInstance();
		int month = calendar.get(Calendar.MONTH);
		int year = calendar.get(Calendar.YEAR);

		curMonth.setText(monthNames[month] + " " + year);

		handler = new DatabaseHandler(this);
		double expense = handler.MonthlyExpense(month + 1, year);
		double income = handler.MonthlyIncome(month + 1, year);

		balanceText.setTextColor(getResources().getColor(R.color.black));
		balanceText.setText("Balance: ");
		DecimalFormat df = new DecimalFormat("####0.00");
		double balance = income - expense;
		if (balance <= 0)
			balanceText.setTextColor(getResources().getColor(R.color.red));
		else
			balanceText.setTextColor(getResources().getColor(R.color.green));

		incomeText.setText("Income:\n" + df.format(income));
		expenseText.append("Expense:\n" + df.format(expense));

		balanceText.append("" + df.format(balance));

		String list[] = { "HOME", "INCOME", "EXPENSE", "CHARTS", "ABOUT" };
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
				R.layout.navlistview, list);
		navlist.setAdapter(adapter);

		incomeButton.setOnClickListener(this);
		expenseButton.setOnClickListener(this);
		add.setOnClickListener(this);
		
		fragmanager = getSupportFragmentManager();
		fragTransaction = fragmanager.beginTransaction();
		
		navlist.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		navlist.setActivated(true);
		navlist.setOnItemClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.manager, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		
		case android.R.id.home:
			if (drawerLayout.isDrawerOpen(navlist))
				drawerLayout.closeDrawer(navlist);
			else
				drawerLayout.openDrawer(navlist);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButton1:
			// startActivity(new Intent(Manager.this,ShowIncome.class));
			startActivity(new Intent(Manager.this,ShowIncome.class));
			break;
		case R.id.imageButton2:
			startActivity(new Intent(Manager.this, ShowExpense.class));
			break;
		case R.id.add:
			startActivity(new Intent(Manager.this, ChooseAdd.class));
			break;
		}
	}

	void loadSelection(int i) {
		navlist.setItemChecked(i, true);
		switch (i) {
		case 0:
			drawerLayout.closeDrawer(navlist);
			startActivity(new Intent(Manager.this,Manager.class));
			break;
				case 1:
			startActivity(new Intent(Manager.this,ShowIncome.class));
			break;
		case 2:
			startActivity(new Intent(Manager.this,ShowExpense.class));
			break;
		case 3:
			startActivity(new Intent(Manager.this,Chart.class));
			break;
		case 4:
			break;

		}
	}

	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState);
		actionBarDrawerToogle.syncState();

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		view.setSelected(true);
		view.setSelected(true);
		loadSelection(position);
		
	}
	@Override
	public void onBackPressed() {
		if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 5000);
	}
}
