package com.example.expensemanager;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.UUID;

public class AddIncomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DatabaseHandler handler;
    TextView mDate, mTextView;
    Spinner mSpinner;
    ImageButton mSelectDate;
    EditText mAmount, mDescription, mMode;
    Spinner mSource;
    String[] sources = {"pocket money", "profits", "salary", "miscellaneous"};
    String Amount, Source, Description, Mode;
    int Date, Month, Year;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Button mSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setElevation(0);
        }

        mAmount = (EditText) findViewById(R.id.input_amount);
        mDescription = (EditText) findViewById(R.id.input_description);
        mMode = (EditText) findViewById(R.id.input_mode);
        mSpinner = (Spinner) findViewById(R.id.spinner1);
        mTextView = (TextView) findViewById(R.id.textView);
        mSelectDate = (ImageButton) findViewById(R.id.imageButton1);
        mSave = (Button)findViewById(R.id.bt_save) ;

        handler = new DatabaseHandler(this);

        pref = getSharedPreferences("edit_income", MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, sources);
        adapter.setDropDownViewResource(R.layout.layout_spinner);
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(this);

        mSelectDate.setOnClickListener(this);
        mSave.setOnClickListener(this);

        Calendar currentDate = Calendar.getInstance();
        Date = currentDate.get(Calendar.DATE);
        Month = currentDate.get(Calendar.MONTH) + 1;
        Year = currentDate.get(Calendar.YEAR);
        mTextView.setText(Date + "/" + (Month) + "/" + Year);

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
                DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        Date = dayOfMonth;
                        Month = monthOfYear + 1;
                        Year = year;
                        mTextView.setText(Date + "/" + Month + "/" + Year);
                    }
                }, Year, Month-1, Date);

                dialog.setTitle("Choose Date");
                dialog.setCancelable(true);
                dialog.show();
                break;
            case R.id.bt_save :
                String check = mAmount.getText().toString();
                if (check == null || check.length() == 0 || check.equals("")) {
                    Toast.makeText(this, "Enter a valid amount.", Toast.LENGTH_SHORT).show();
                } else {
                    String ID = UUID.randomUUID().toString();

                    double Amount = Double.parseDouble(mAmount.getText().toString());

                    String Description = mDescription.getText().toString();
                    String Mode = mMode.getText().toString();
                    switch(mSpinner.getSelectedItemPosition())
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
                   // Log.e("TAG",obj.getAmount()+" "+obj.getDate());
                    //Log.e("TAG", ID + " " + Amount + Source + Description + Date +Month+Year+Mode);

                    IncomeModel obj = new IncomeModel(ID, Amount, Source,
                            Description, Date, Month, Year, Mode);


                    handler.insertIncome(obj);
                   Toast.makeText(this, "Income saved.", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(AddIncomeActivity.this, Manager.class));
                }
                break;

        }
    }



}



