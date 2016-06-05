package com.example.expensemanager;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Income_CustomAdapter extends ArrayAdapter<IncomeModel> {

	Context context;
	List<IncomeModel> model;
	ImageView image;
	TextView amt,source,date;

	public Income_CustomAdapter(Context context, List<IncomeModel> model) {
		super(context, NO_SELECTION, model);
		this.context = context;
		this.model = model;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.list, null);
		} else
			view = convertView;
		
		DecimalFormat df = new DecimalFormat("####0.00");
		
		amt=(TextView)view.findViewById(R.id.textView3);
		date=(TextView)view.findViewById(R.id.textView2);
		source=(TextView)view.findViewById(R.id.textView1);
		image=(ImageView)view.findViewById(R.id.imageView1);
		
		
		image.setImageResource(R.drawable.income_f);
		double amount=model.get(position).getAmount();
		amt.setText(""+df.format(amount));
		date.setText(""+model.get(position).getDate()+"/"+model.get(position).getMonth()+"/"+model.get(position).getYear());
		source.setText(""+model.get(position).getSource().toString());
		return view;


	}

}
