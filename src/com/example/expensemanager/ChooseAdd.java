package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class ChooseAdd extends AppCompatActivity implements OnCheckedChangeListener {

	RadioGroup radiogroup;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_add);
		
		toolbar=(Toolbar)findViewById(R.id.toolbar);
		toolbar.setTitle("Add Transaction");
		setSupportActionBar(toolbar);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		radiogroup=(RadioGroup)findViewById(R.id.radioGroup1);
		
		radiogroup.setOnCheckedChangeListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.choose_add, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			startActivity(new Intent(ChooseAdd.this,Manager.class));
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch(checkedId)
		{
		case R.id.radio0:
			startActivity(new Intent(ChooseAdd.this,AddIncome.class));
			break;
		case R.id.radio1:
			startActivity(new Intent(ChooseAdd.this,AddExpense.class));
			break;
		}
	}
}
