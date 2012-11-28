package com.shining.qrcode;

import android.app.ActivityGroup;  
import android.app.AlertDialog;
 
import android.content.DialogInterface;
import android.content.Intent;  
import android.graphics.Color;  
import android.graphics.drawable.ColorDrawable;  
import android.os.Bundle;  
import android.view.Gravity;  
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;  
import android.view.Window;  
import android.view.ViewGroup.LayoutParams;  
import android.view.WindowManager;
import android.widget.AdapterView;  
import android.widget.GridView;  
import android.widget.LinearLayout;   
import android.widget.AdapterView.OnItemClickListener;  
 
public class QRCode extends ActivityGroup {

	private GridView topBar;  
	private ImageAdapterForTab topImgAdapter;  
	public LinearLayout container;
	  
	int[] topbar_image_array = { 
			  	R.drawable.qrencode_background,  
	            R.drawable.qrdecode_background,
	            R.drawable.qrcard_background};  
 
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        setContentView(R.layout.activity_qrcode);
     
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
                | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_UNSPECIFIED);
        
        
        topBar = (GridView)findViewById(R.id.topBar);  
        topBar.setNumColumns(topbar_image_array.length);
        topBar.setSelector(new ColorDrawable(Color.TRANSPARENT));
        topBar.setGravity(Gravity.CENTER);
        topBar.setVerticalSpacing(0);
        int width = this.getWindowManager().getDefaultDisplay().getWidth()  
                / topbar_image_array.length;  
        int height= this.getWindowManager().getDefaultDisplay().getHeight()/10;
        topImgAdapter = new ImageAdapterForTab(this, topbar_image_array, width,height,R.drawable.topbar);  
        topBar.setAdapter(topImgAdapter);
        topBar.setOnItemClickListener(new ItemClickEvent());
        container = (LinearLayout) findViewById(R.id.Container);  
        
        SwitchActivity(1);
    }
	
	
	class ItemClickEvent implements OnItemClickListener {  
		  
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,  
                long arg3) {  
            SwitchActivity(arg2);  
        }  
    }  
	
	 void SwitchActivity(int id)  
	    {  
	        topImgAdapter.SetFocus(id);
	        container.removeAllViews();
	        Intent intent =null;  
	        if (id == 0) {  
	        	intent=new Intent(QRCode.this,QREncode.class);
	        } else if (id == 1) {  
	        	 intent=new Intent(QRCode.this,QRDecode.class);
	        } else if (id==  2){
	        	intent=new Intent(QRCode.this,QRCard.class);
	        }
	     

	        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	     
	        final Window subActivity = getLocalActivityManager().startActivity(  
	                "subActivity", intent);  
	        
	        container.addView(subActivity.getDecorView(),  
	        		LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT);  
	    }  
	 
	 public void ExitProgram(){
		 
		  Intent exit = new Intent(Intent.ACTION_MAIN);
          exit.addCategory(Intent.CATEGORY_HOME);
          exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(exit);
          System.exit(0);
                 
		 
	 }
	 
		  
	 public boolean onKeyDown(int keyCode, KeyEvent event) {
			
			if(keyCode==KeyEvent.KEYCODE_BACK) {
				
				new AlertDialog.Builder(this)
				 .setTitle("退出")
				 .setMessage("确定退出吗？")
				 .setIcon(R.drawable.ic_launcher)
				 .setPositiveButton("确定", new DialogInterface.OnClickListener() {
				                 
				      public void onClick(DialogInterface dialog, int which) {
				                
				    	  ExitProgram();
				    	
				      }
				            
				 })
				             
				 .setNegativeButton("取消", new DialogInterface.OnClickListener() {
				                 
				        public void onClick(DialogInterface dialog, int which) {
				                  
				           dialog.cancel();
				                 
				        }
			            
				 })
			             
				 .show();
				
				return true;
			}
			 
			return super.onKeyDown(keyCode, event);
		}
	 
	 public boolean onCreateOptionsMenu(Menu menu) {
	 
	
		 menu.add(0, 1, 1, "退出");
	
	    return super.onCreateOptionsMenu(menu);
	 }
	 
	
	     public boolean onOptionsItemSelected(MenuItem item) {
	   
	    	 if(item.getItemId() == 1){
	    		 
	    		  ExitProgram();
		        	 
	    	 }
	    	
	         return true;
	     }
}
