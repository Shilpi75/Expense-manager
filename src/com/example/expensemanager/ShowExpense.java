package com.example.expensemanager;

import java.text.DecimalFormat;
import java.util.List;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowExpense extends AppCompatActivity implements
		OnItemClickListener {
	ListView lv;
	List<Model> list;
	DatabaseHandler handler;
	CustomAdapter adapter;
	Toolbar toolbar;
	TextView textView;
	String ID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_expense);

		toolbar = (Toolbar) findViewById(R.id.toolbar);
		textView = (TextView) findViewById(R.id.TextView1);
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Expense");
		getSupportActionBar().setIcon(R.drawable.image2);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lv = (ListView) findViewById(R.id.listView1);
		handler = new DatabaseHandler(this);
		list = handler.getExpense();
		if (list.isEmpty()) {
			// DisplayToast newToast=new DisplayToast();
			// newToast.myToast(this, "No expense added!", 0,
			// R.drawable.feedly);
			Toast toast = new Toast(this);
			toast.makeText(this, "No expense added!", 0).show();
			startActivity(new Intent(ShowExpense.this, Manager.class));
		}
		adapter = new CustomAdapter(this, list);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(this);
		registerForContextMenu(lv);
		double sum = handler.getTotalExpense();
		DecimalFormat df = new DecimalFormat("####0.00");
		textView.setText("Total Expense:  " + df.format(sum));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show_expense, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			onBackPressed();
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		SharedPreferences pref = getSharedPreferences("viewExpense",
				MODE_PRIVATE);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("expenseID", list.get(position).getId());
		editor.commit();
		startActivity(new Intent(ShowExpense.this, ViewExpense.class));
	}

	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		if (v.getId() == R.id.listView1) {
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.menu.menu, menu);

		}
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		ID = list.get(info.position).getId();
		switch (item.getItemId()) {

		case R.id.delete:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("Delete Expense");
			dialog.setCancelable(true);
			dialog.setMessage("Are you sure to delete this transaction?");
			dialog.setNeutralButton("NO", null);
			dialog.setPositiveButton("YES",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							handler.deleteExpense(ID);
							DisplayToast toast = new DisplayToast();
							toast.myToast(ShowExpense.this,
									"Transcation Deleted", 0, R.drawable.anydo);
							startActivity(new Intent(ShowExpense.this,
									ShowExpense.class));
						}
					});
			dialog.show();

			break;
		case R.id.edit:
			SharedPreferences pref = getSharedPreferences("edit_expense",
					MODE_PRIVATE);
			SharedPreferences.Editor editor = pref.edit();
			editor.putBoolean("isEditExpense", true);
			editor.putString("id", ID);
			editor.commit();
			startActivity(new Intent(ShowExpense.this, EditExpense.class));

			break;

		}
		return super.onContextItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(ShowExpense.this, Manager.class));
	}
}
