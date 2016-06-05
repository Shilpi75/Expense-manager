package com.example.expensemanager;



import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

public class ScreenSplash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_screen_splash);
		final ImageView splashImageView = (ImageView) findViewById(R.id.imageView1);
		splashImageView.setBackgroundResource(R.drawable.screen_splash);
		final AnimationDrawable frameAnimation =(AnimationDrawable)splashImageView.getBackground(); 
		splashImageView.post(new Runnable(){
		    @Override
		    public void run() {
		        frameAnimation.start();                
		    }            
		}); 
		
		
		
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				startActivity(new Intent(ScreenSplash.this, Manager.class));
				finish();

			}
		}, 5000);
	}

}
