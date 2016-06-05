package com.example.expensemanager;

import java.util.Calendar;
import java.util.UUID;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.iangclifton.android.floatlabel.FloatLabel;

public class EditIncome extends AppCompatActivity implements
		OnItemSelectedListener, OnClickListener {

	DatabaseHandler handler;
	TextView date, textView;

	ImageButton selectDate;
	FloatLabel amount, description, mode;
	Spinner source;
	String[] sources = { "pocket money", "profits", "salary", "miscellaneous" };
	String Amount, Source, Description, Mode;
	int Date, Month, Year;
	Toolbar myToolbar;
	SharedPreferences pref;
	SharedPreferences.Editor editor;
	String ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_income);

		handler = new DatabaseHandler(this);

		myToolbar = (Toolbar) findViewById(R.id.toolbar);
		myToolbar.setTitle("Add Income");
		setSupportActionBar(myToolbar);
		getSupportActionBar().setHomeButtonEnabled(true);

		amount = (FloatLabel) findViewById(R.id.amount);
		description = (FloatLabel) findViewById(R.id.description);
		mode = (FloatLabel) findViewById(R.id.mode);

		source = (Spinner) findViewById(R.id.spinner1);
		textView = (TextView) findViewById(R.id.textView);
		selectDate = (ImageButton) findViewById(R.id.imageButton1);
		
		pref=getSharedPreferences("edit_income",MODE_PRIVATE);
		

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, sources);
		adapter.setDropDownViewResource(R.layout.layout_spinner);
		source.setAdapter(adapter);

		source.setOnItemSelectedListener(this);

		selectDate.setOnClickListener(this);

		Calendar currentDate = Calendar.getInstance();
		Date = currentDate.get(Calendar.DATE);
		Month = currentDate.get(Calendar.MONTH) + 1;
		Year = currentDate.get(Calendar.YEAR);
		textView.setText(Date + "/" + (Month) + "/" + Year);
		
		if(pref.getBoolean("isEditIncome", false)==true)
		{
			ID=pref.getString("id", null);
			IncomeModel obj=handler.getIncome(ID);
			amount.getEditText().setText(""+obj.getAmount());
			description.getEditText().setText(obj.getDescription());
			textView.setText(obj.getDate()+"/"+obj.getMonth()+"/"+obj.getYear());
			Date=obj.getDate();
			Month=obj.getMonth();
			Year=obj.getYear();
			mode.getEditText().setText(obj.getMode());
			String selsource=obj.getSource();
			Log.e("EditIncome",selsource);
			if(selsource=="Pocket Money")
				source.setSelection(0);
			else if(selsource.equals("Profits"))
				source.setSelection(1);
			else if(selsource.equals("Salary"))
				source.setSelection(2);
			else if(selsource.equals("Miscellaneous"))
				source.setSelection(3);
			else
				source.setSelection(0);
				
			
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_income, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.done) {
			handler.deleteIncome(ID);
			String check = amount.getEditText().getText().toString();
			if (check == null || check.length() == 0 || check.equals("")) {
				DisplayToast toast = new DisplayToast();
				toast.myToast(EditIncome.this, "Enter a valid amount.", 0,
						R.drawable.error);
			} else {
				String ID = UUID.randomUUID().toString();

				double Amount = Double.parseDouble(amount.getEditText()
						.getText().toString());

				String Description = description.getEditText().getText()
						.toString();
				String Mode = mode.getEditText().getText().toString();
				switch(source.getSelectedItemPosition())
				{
				case 0:
					Source="Pocket Money";
					break;
				case 1:
					Source="Profits";
					break;
				case 2:
					Source="Salary";
					break;
				case 3:
					Source="Miscellaneous";
					break;
					default:
						Source="Pocket Money";
				}
				IncomeModel obj = new IncomeModel(ID, Amount, Source,
						Description, Date, Month, Year, Mode);

				handler.insertIncome(obj);
				DisplayToast toast = new DisplayToast();
				toast.myToast(EditIncome.this, "Income Added", 0,
						R.drawable.anydo);
				startActivity(new Intent(EditIncome.this, Manager.class));
			}

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			Source = "Pocket Money";
			break;
		case 1:
			Source = "Profit";
			break;
		case 3:
			Source = "Salary";
			break;
		case 4:
			Source = "Miscellaneous";
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

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
					}, Year, Month - 1, Date);
			dialog.setTitle("Choose Date");
			dialog.setCancelable(true);
			dialog.show();
			break;

		}
	}

	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(EditIncome.this, Manager.class));
	}
}
