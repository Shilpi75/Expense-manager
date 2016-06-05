package com.example.expensemanager;

import java.util.Calendar;
import java.util.UUID;

import org.achartengine.model.CategorySeries;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.iangclifton.android.floatlabel.FloatLabel;

public class EditExpense extends AppCompatActivity implements OnClickListener,
		OnItemSelectedListener {
	Toolbar myToolbar;
	FloatLabel amount;
	FloatLabel description, mode;
	
	Spinner category;
	TextView textView;
	ImageButton selectDate;
	String[] categories = { "Food", "Friends", "Recharge", "Travel",
			"Entertainment", "Stationary", "Medical", "Personel care",
			"Miscellaneos" };
	String selCategory = "None",ID;
	int Date, Month, Year;
	DatabaseHandler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_expense);

		handler = new DatabaseHandler(this);

		myToolbar = (Toolbar) findViewById(R.id.toolbar);
		myToolbar.setTitle("Add Expense");
		setSupportActionBar(myToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);

		amount = (FloatLabel) findViewById(R.id.amount);
		description = (FloatLabel) findViewById(R.id.description);
		mode = (FloatLabel) findViewById(R.id.mode);
		
		category = (Spinner) findViewById(R.id.spinner1);
		textView = (TextView) findViewById(R.id.textView);
		selectDate = (ImageButton) findViewById(R.id.imageButton1);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, categories);
		adapter.setDropDownViewResource(R.layout.layout_spinner);
		category.setAdapter(adapter);

		category.setOnItemSelectedListener(this);

		
		selectDate.setOnClickListener(this);

		Calendar currentDate = Calendar.getInstance();
		Date = currentDate.get(Calendar.DATE);
		Month = currentDate.get(Calendar.MONTH)+1;
		Year = currentDate.get(Calendar.YEAR);
		textView.setText(Date + "/" + (Month ) + "/" + Year);
		SharedPreferences pref=getSharedPreferences("edit_expense", MODE_PRIVATE);
		if(pref.getBoolean("isEditExpense", false))
		{
			ID=pref.getString("id", null);
			Model obj=handler.getExpense(ID);
			amount.getEditText().setText(""+obj.getAmount());
			description.getEditText().setText(obj.getDescription());
			Date=obj.getDate();
			Month=obj.getMonth();
			Year=obj.getYear();
			textView.setText(Date+"/"+Month+"/"+Year);
			mode.getEditText().setText(obj.getMode());
			String Category=obj.getCategory();
			if(Category.equalsIgnoreCase("Food"))
				category.setSelection(0);
			else if(Category.equalsIgnoreCase("Friends"))
				category.setSelection(1);
			else if(Category.equalsIgnoreCase("Recharge"))
				category.setSelection(2);
			else if(Category.equalsIgnoreCase("Travel"))
				category.setSelection(3);
			else if(Category.equalsIgnoreCase("Entertainment"))
				category.setSelection(4);
			else if(Category.equalsIgnoreCase("Stationary"))
				category.setSelection(5);
			else if(Category.equalsIgnoreCase("Medical"))
				category.setSelection(6);
			else if(Category.equalsIgnoreCase("Personal Care"))
				category.setSelection(7);
			else if(Category.equalsIgnoreCase("Miscellaneous"))
				category.setSelection(8);
			else
				category.setSelection(0);
				
			
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.done) {
			String check=amount.getEditText().getText().toString();
			if(check==null || check.length()==0 || check.equals(""))
			{
				DisplayToast toast =new  DisplayToast();
				toast.myToast(EditExpense.this,"Enter a valid amount.", 0, R.drawable.error);
			}
			else
			{
			String ID = UUID.randomUUID().toString();

			double Amount = Double.parseDouble(amount.getEditText().getText()
					.toString());
			

			String Description = description.getEditText().getText().toString();
			String Mode = mode.getEditText().getText().toString();
			switch (category.getSelectedItemPosition()) {
			case 0:
				selCategory = "Food";
				break;
			case 1:
				selCategory = "Friends";
				break;
			case 2:
				selCategory = "Recharge";
				break;
			case 3:
				selCategory="Travel";
				break;
			case 4:
				selCategory="Entertainment";
				break;
			case 5:
				selCategory="Stationary";
				break;
			case 6:
				selCategory="Medical";
				break;
			case 7:
				selCategory="Personal Care";
				break;
			case 8:
				selCategory="Miscellaneous";
				break;
			}
			Model obj = new Model(ID, Amount, selCategory, Description, Date,
					Month, Year, Mode);

			handler.insertExpense(obj);
			DisplayToast toast = new DisplayToast();
			toast.myToast(EditExpense.this, "Expense Added", 0, R.drawable.anydo);
			startActivity(new Intent(EditExpense.this,Manager.class));
			}

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageButton1:

			DatePickerDialog dialog = new DatePickerDialog(this,
					new OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							// TODO Auto-generated method stub

							Date = dayOfMonth;
							Month = monthOfYear + 1;
							Year = year;
							textView.setText(Date + "/" + Month + "/" + Year);
						}
					}, Year, Month-1, Date);
			dialog.setTitle("Choose Date");
			dialog.setCancelable(true);
			dialog.show();
			break;

		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			selCategory = "Food";
			break;
		case 1:
			selCategory = "Friends";
			break;
		case 2:
			selCategory = "Recharge";
			break;
		case 3:
			selCategory="Travel";
			break;
		case 4:
			selCategory="Entertainment";
			break;
		case 5:
			selCategory="Stationary";
			break;
		case 6:
			selCategory="Medical";
			break;
		case 7:
			selCategory="Personal Care";
			break;
		case 8:
			selCategory="Miscellaneous";
			break;
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(EditExpense.this,Manager.class));
	}
}
