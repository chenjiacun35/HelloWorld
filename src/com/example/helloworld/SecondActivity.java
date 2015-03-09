package com.example.helloworld;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity{

	
	TextView textview =null;
	Button back = null;
	private  String msg ;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second);
//		IntentFilter filter = new IntentFilter("com.example.helloworld.PushReceiver");
//		receiver = new MyReceiver();
//		registerReceiver(receiver, filter);
		back = (Button)findViewById(R.id.backbutton);
		textview = (TextView)findViewById(R.id.stext);
		textview.setText(MyReceiver.getMsg());
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				onBackPressed();
			}
		});
		
	}
	@Override
	public void onPause()
	{
		super.onPause();
	}
	
	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
	}
}
