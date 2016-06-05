package com.example.expensemanager;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class Custom_float extends TextView{
	public Custom_float(Context context, AttributeSet attrs, int defStyleAttr,
			int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		customfont();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public Custom_float(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		customfont();
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public Custom_float(Context context, AttributeSet attrs) {
		super(context, attrs);
		customfont();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param context
	 */
	public Custom_float(Context context) {
		super(context);
		customfont();
		// TODO Auto-generated constructor stub
	}

	public void customfont() {
		Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
				"Sansation-Regular.ttf");
		setTypeface(tf);

	}

}
