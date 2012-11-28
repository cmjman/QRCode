package com.shining.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;



public class Welcome extends Activity{

	
	
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		new Handler().postDelayed(new Runnable(){  
			  
			
			public void run() {  
			    Intent intent = new Intent(Welcome.this,QRCode.class);  
			    startActivity(intent);  
			    Welcome.this.finish();  
			}  
			  
			}, 2500);  
		
	}
 	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode==KeyEvent.KEYCODE_BACK) {
			return false;
		}
		return false;
	}
	
}