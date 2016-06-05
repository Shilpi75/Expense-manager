package com.example.expensemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayToast {
	public static void myToast(Context context, String message, int duration,
			int image) {
		LayoutInflater inflator = LayoutInflater.from(context);
		View mainLayout = inflator.inflate(R.layout.layout_toast, null);
		View rootLayout = mainLayout.findViewById(R.id.rootLayout);
		TextView mssg=(TextView)mainLayout.findViewById(R.id.textView1);
		mssg.setText(message);
		ImageView img=(ImageView)mainLayout.findViewById(R.id.imageView1);
		img.setImageResource(image);
		Toast toast=new Toast(context);
		toast.setView(rootLayout);
		toast.setDuration(duration);
		
				toast.show();

	}
}
