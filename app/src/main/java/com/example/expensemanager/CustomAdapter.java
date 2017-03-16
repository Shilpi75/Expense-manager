package com.example.expensemanager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Model> {
	TextView category;
	TextView amt;
	TextView date;
	Context context;
	List<Model> model;

	public CustomAdapter(Context context, List<Model> model) {
		super(context, NO_SELECTION, model);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.model = model;
	}

//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//		// TODO Auto-generated method stub
//		View view;
//		if (convertView == null) {
//			LayoutInflater inflater = (LayoutInflater) context
//					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			//view = inflater.inflate(R.layout.list, null);
//		} else
//			view = convertView;
////		amt=(TextView)view.findViewById(R.id.textView3);
////		date=(TextView)view.findViewById(R.id.textView2);
////		category=(TextView)view.findViewById(R.id.textView1);
//		DecimalFormat df = new DecimalFormat("####0.00");
//		amt.setText(""+df.format(model.get(position).getAmount()));
//		date.setText(""+model.get(position).getDate()+"/"+model.get(position).getMonth()+"/"+model.get(position).getYear());
//		category.setText(""+model.get(position).getCategory().toString());
//		return view;
//	}

}
