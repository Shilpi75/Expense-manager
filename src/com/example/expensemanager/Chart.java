package com.example.expensemanager;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.iangclifton.android.floatlabel.FloatLabel;

public class Chart extends ActionBarActivity implements OnClickListener {
	int year;
	FloatLabel chooseYear;
	Button monthlyChart, categoryChart;
	DatabaseHandler handler;
	String check;
	Intent intent;
	Toolbar toolbar;
	TextView ChooseChart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);

		handler = new DatabaseHandler(this);
		
		toolbar=(Toolbar)findViewById(R.id.toolbar);
		toolbar.setTitle("Charts");
		setSupportActionBar(toolbar);
		getSupportActionBar().setTitle("Charts");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		chooseYear = (FloatLabel) findViewById(R.id.editText1);
		monthlyChart = (Button) findViewById(R.id.button1);
		categoryChart = (Button) findViewById(R.id.button2);
		ChooseChart=(TextView)findViewById(R.id.ChooseChart);
		
		Typeface tf=Typeface.createFromAsset(getAssets(), "Sansation-Regular.ttf");
		ChooseChart.setTypeface(tf);
		monthlyChart.setTypeface(tf);
		categoryChart.setTypeface(tf);

		monthlyChart.setOnClickListener(this);
		categoryChart.setOnClickListener(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.chart, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == android.R.id.home) {
			startActivity(new Intent(Chart.this,Manager.class));
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.button1:
			check = chooseYear.getEditText().getText().toString();
			if (check == null || check.length() < 4 || check.equals("") || Double.parseDouble(check)<2000) {
				DisplayToast toast = new DisplayToast();
				toast.myToast(Chart.this, "Enter valid year.", 0,
						R.drawable.error);
			} else {
				int margins[] = { 30, 35, 30, 30 };
				year = Integer.parseInt(chooseYear.getEditText().getText()
						.toString());
				String months[] = { "Jan", "Feb", "March", "April", "May",
						"June", "July", "Aug", "Sept", "Oct", "Nov", "Dec" };
				double expense[] = new double[13];
				double income[] = new double[13];

				CategorySeries series = new CategorySeries("Monthly Expense");
				CategorySeries series2 = new CategorySeries("Monthly Income");
				for (int i = 1; i <= 12; i++) {
					expense[i] = handler.MonthlyExpense(i, year);
					income[i] = handler.MonthlyIncome(i, year);
					series.add(months[i - 1].toString(), expense[i]);
					series2.add(months[i - 1].toString(), income[i]);
				}
				XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
				dataset.addSeries(series.toXYSeries());
				dataset.addSeries(series2.toXYSeries());

				XYSeriesRenderer renderer1 = new XYSeriesRenderer();
				renderer1.setColor(Color.RED);
				XYSeriesRenderer renderer2 = new XYSeriesRenderer();
				renderer2.setColor(Color.GREEN);

				XYMultipleSeriesRenderer mrenderer = new XYMultipleSeriesRenderer();
				mrenderer.addSeriesRenderer(renderer1);
				mrenderer.addSeriesRenderer(renderer2);
				mrenderer.setApplyBackgroundColor(true);
				mrenderer.setBackgroundColor(Color.BLACK);
				mrenderer.setBarSpacing(1f);
				mrenderer.setOrientation(Orientation.HORIZONTAL);
				mrenderer.setShowLabels(true);
				mrenderer.setYTitle("Amount");
				mrenderer.setXLabels(0);
				mrenderer.setXLabelsAngle(90);
				mrenderer.setAxesColor(Color.CYAN);
				mrenderer.setChartTitleTextSize(20);
				mrenderer.setAxisTitleTextSize(20);
				mrenderer.setLabelsColor(Color.YELLOW);
				mrenderer.setLabelsTextSize(20);
				mrenderer.setMargins(margins);
				// mrenderer.set
				mrenderer.setChartTitle("Monthly Income-Expense");

				for (int i = 1; i <= 12; i++) {
					mrenderer.addTextLabel(i, months[i - 1]);
				}

			intent = ChartFactory
						.getBarChartIntent(this, dataset, mrenderer,
								org.achartengine.chart.BarChart.Type.DEFAULT);
				startActivity(intent);
			}
			break;
		case R.id.button2:
			int count = 0;
			check = chooseYear.getEditText().getText().toString();
			if (check == null || check.length() < 4 || check.equals("")) {
				DisplayToast toast = new DisplayToast();
				toast.myToast(Chart.this, "Enter valid year.", 0,
						R.drawable.error);
			} else {
				year = Integer.parseInt(chooseYear.getEditText().getText()
						.toString());
				String categories[] = { "Food", "Friends", "Recharge",
						"Travel", "Entertainment", "Stationary", "Medical",
						"Personel care", "Miscellaneos" };
				int colors[] = { Color.YELLOW, Color.BLUE, Color.GREEN,
						Color.MAGENTA, Color.RED, Color.WHITE, Color.GRAY,
						Color.CYAN, Color.LTGRAY };
				CategorySeries pie_series = new CategorySeries("Category chart");
				for (int i = 0; i <= 8; i++) {
					double sum = handler.CategoryExpense(categories[i], year);
					if (sum > 0) {
						pie_series.add(categories[i], sum);
						count++;
					}

				}
				DefaultRenderer renderer = new DefaultRenderer();
				for (int i = 0; i < count; i++) {
					SimpleSeriesRenderer r = new SimpleSeriesRenderer();
					r.setColor(colors[i]);
					renderer.addSeriesRenderer(r);
				}
				renderer.setApplyBackgroundColor(true);
				renderer.setBackgroundColor(Color.BLACK);
				renderer.setLabelsTextSize(20);
				renderer.setLegendTextSize(20);

				intent = ChartFactory.getPieChartIntent(this, pie_series,
						renderer, "");

				startActivity(intent);
			}
			break;

		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		startActivity(new Intent(Chart.this,Manager.class));
	}
}
