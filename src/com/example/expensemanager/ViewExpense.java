package com.example.expensemanager;


import java.text.DecimalFormat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ViewExpense extends AppCompatActivity {
	SharedPreferences pref;
	String ID;
	Model obj;
	DatabaseHandler handler;
	TextView textView, amountText, categoryText, descriptionText, dateText,
			modeText;
	Toolbar mytoolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_expense);

		mytoolbar = (Toolbar) findViewById(R.id.toolbar);
		mytoolbar.setTitle("Transaction");
		setSupportActionBar(mytoolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setIcon(R.drawable.ic_launcher);

		pref = getSharedPreferences("viewExpense", MODE_PRIVATE);
		ID = pref.getString("expenseID", null);

		handler = new DatabaseHandler(this);
		obj = handler.getExpense(ID.toString());

		textView = (TextView) findViewById(R.id.textView);
		amountText = (TextView) findViewById(R.id.amountText);
		amountText.setTextColor(getResources().getColor(R.color.red));
		categoryText = (TextView) findViewById(R.id.categoryText);
		descriptionText = (TextView) findViewById(R.id.descriptionText);
		dateText = (TextView) findViewById(R.id.dateText);
		modeText = (TextView) findViewById(R.id.modeText);

		textView.setText("Expense");
		// Typeface tf=Typeface.createFromAsset(getAssets(), "XeroxSerif.ttf");
		// textView.setTypeface(tf);
		DecimalFormat df = new DecimalFormat("####0.00");  
		amountText.setText(df.format(obj.getAmount()) + "");
		categoryText.setText(obj.getCategory());
		descriptionText.setText(obj.getDescription());
		dateText.setText(obj.getDate() + "/" + obj.getMonth() + "/"
				+ obj.getYear());
		modeText.setText(obj.getMode());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch(id)
		{
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.edit:
			SharedPreferences pref = getSharedPreferences("edit_expense",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean("isEditExpense", true);
			editor.putString("id", ID);
			editor.commit();
			startActivity(new Intent(ViewExpense.this, EditExpense.class));
			
			break;
		case R.id.delete:
			
			AlertDialog.Builder dialog=new AlertDialog.Builder(this);
			dialog.setTitle("Delete Expense");
			dialog.setCancelable(true);
			dialog.setMessage("Are you sure to delete this transaction?");
			dialog.setNeutralButton("NO", null);
			dialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					handler.deleteExpense(ID);
					DisplayToast toast=new DisplayToast();
					toast.myToast(ViewExpense.this,"Transcation Deleted",0, R.drawable.anydo);
					startActivity(new Intent(ViewExpense.this,ShowExpense.class));
				}
			}); 
			dialog.show();
			
			break;
		}
		
		return super.onOptionsItemSelected(item);
	}
	@Override
	
		public void onBackPressed() {
			// TODO Auto-generated method stub
			startActivity(new Intent(ViewExpense.this, ShowExpense.class));
		}
	
}
