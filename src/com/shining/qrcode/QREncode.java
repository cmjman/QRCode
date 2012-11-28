package com.shining.qrcode;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.*;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.*;
import android.widget.*;




public class QREncode extends Activity{
	
	private static final String SAVE_PICTURE_PATH=Environment.getExternalStorageDirectory().toString()+"/QRCard";
	
	private Button draw;
	private Button save;
	
	private EditText Name;
	private EditText Email;
	private EditText QQ;
	private EditText Phone;
	
	private ViewPager mViewPager;
	private MPagerAdapter mPagerAdapter;
	
	private Bitmap mBitmap;
	private Bitmap bm;
	
	private String name;
	private String email;
	private String qq;
	private String phone;

	
	
	public void onCreate(Bundle savedInstanceState){
		
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_qrencode);
			
			DisplayMetrics dm=new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(dm);
			
			mViewPager = (ViewPager)findViewById(R.id.pager); 
			
			mPagerAdapter = new MPagerAdapter(this);  
			mViewPager.setAdapter(mPagerAdapter);  
			mPagerAdapter.change(getList());  
			    
			draw=(Button)findViewById(R.id.draw);
			draw.setOnClickListener(new Button.OnClickListener(){
				
				public void onClick(View view){
					
					name=Name.getText().toString();
					email=Email.getText().toString();
					phone=Phone.getText().toString();
					qq=QQ.getText().toString();
					
					
					if(Name.getText().toString()!=""){
						String str="MECARD:"+
								"N:"+name+";"+
								"EMAIL:"+email+";"+
								"TEL:"+phone+";"+
								"QQ:"+qq+";;";
						Encode(str,10);
					}
				}
			});
			
			
			
			
			save=(Button)findViewById(R.id.save);
			save.setOnClickListener(new Button.OnClickListener(){
				
				public void onClick(View view){
					if(mBitmap==null)
						Toast.makeText(QREncode.this, "请先生成二维码再保存！",Toast.LENGTH_SHORT).show();
					else saveToAlbum();
				}
				
			});
			
			Name=(EditText)findViewById(R.id.name);
			
			Name.setOnKeyListener(new EditText.OnKeyListener(){
				
				public boolean onKey(View v,int keyCode,KeyEvent event){
					return false;
				}
			});
			
			Email=(EditText)findViewById(R.id.email);
			
			Email.setOnKeyListener(new EditText.OnKeyListener(){
				
				public boolean onKey(View v,int keyCode,KeyEvent event){
					return false;
				}
			});
			
			QQ=(EditText)findViewById(R.id.qq);
			
			QQ.setOnKeyListener(new EditText.OnKeyListener(){
				
				public boolean onKey(View v,int keyCode,KeyEvent event){
					return false;
				}
			});
			
			Phone=(EditText)findViewById(R.id.phone);
			
			Phone.setOnKeyListener(new EditText.OnKeyListener(){
				
				public boolean onKey(View v,int keyCode,KeyEvent event){
					return false;
				}
			});
		}
	
	  private List<Integer> getList() {  
	        List<Integer> list = new ArrayList<Integer>();  
	        list.add(R.drawable.bg_01);  
	        list.add(R.drawable.bg_02);  
	        list.add(R.drawable.bg_03);  
	        list.add(R.drawable.bg_04);  
	        list.add(R.drawable.bg_05);  
	        return list;  
	    }  
	
	public void Encode(String strEncoding,int qrcodeVersion){
		
		try{
			
			com.swetake.util.Qrcode qrcode=new com.swetake.util.Qrcode();
			
			qrcode.setQrcodeErrorCorrect('H');
			
			qrcode.setQrcodeEncodeMode('B');
			
			qrcode.setQrcodeVersion(qrcodeVersion);
			
			
			byte[] bytesEncoding=strEncoding.getBytes("utf-8");
			if(bytesEncoding.length>0&&bytesEncoding.length<120){
				boolean[][] bEncoding=qrcode.calQrcode(bytesEncoding);
				
				drawQRCode(bEncoding,R.color.black);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	private void drawQRCode(boolean[][] bRect,int colorFill){
		
		int intPaddingLeft=270;
		int intPaddingTop=100;
		
		//  DisplayMetrics dm = getResources().getDisplayMetrics();
		  int  width = 480; 
		  int  height = 288; 
		  int  mid_x = (int)width/2;
		  int  mid_y = (int)height/2;
	
		 mBitmap=Bitmap.createBitmap(width,height,Config.ARGB_8888);
		 
		
		bm=mPagerAdapter.getBitmap();
		
		Canvas mCanvas=new Canvas(mBitmap);
	//	mCanvas.drawColor(getResources().getColor(R.color.white));
		
		Paint mPaint=new Paint();
		
		mCanvas.drawBitmap(bm, 0, 0, null);
		
		 String familyName = "仿宋";
		 Typeface font = Typeface.create(familyName,Typeface.BOLD); 
		
		 mPaint.setColor(Color.BLUE);
		 mPaint.setTypeface(font);
		 mPaint.setTextSize(25);
		 
		mPaint.setStyle(Paint.Style.FILL);
		mPaint.setColor(colorFill);
		mPaint.setStrokeWidth(1.0F);
		
		for(int i=0;i<bRect.length;i++){
			for(int j=0;j<bRect.length;j++){
				if(bRect[j][i]){
					mCanvas.drawRect(new Rect(
							intPaddingLeft+j*3+2,
							intPaddingTop+i*3+2,
							intPaddingLeft+j*3+2+3,
							intPaddingTop+i*3+2+3
							),mPaint);
				}
			}
		}
		
		mCanvas.drawText(name, mid_x-180, mid_y-90, mPaint);
		mCanvas.drawText("Email:"+email, mid_x-180, mid_y-50, mPaint);
		mCanvas.drawText("Tel:"+phone, mid_x-180, mid_y-10, mPaint);
		mCanvas.drawText("QQ:"+qq, mid_x-180, mid_y+30, mPaint);
		
		
		mPagerAdapter.setBitmap(mBitmap);
		
	}
	
	public void saveToAlbum(){
		
		File path=new File(SAVE_PICTURE_PATH);
		
		if(!path.exists()){
			path.mkdir();
		}
		
		File f=new File(SAVE_PICTURE_PATH + File.separator+"QRCard_"+System.currentTimeMillis()+".jpg");
			
		try{
			
			BufferedOutputStream os=new BufferedOutputStream(new FileOutputStream(f));
			mBitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
			os.flush();
			os.close();
			
			Toast.makeText(QREncode.this, "保存名片成功！",
                    Toast.LENGTH_SHORT).show();
			
			mPagerAdapter.setBitmap(bm);
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
