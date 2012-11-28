package com.shining.qrcode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;


import android.widget.LinearLayout.LayoutParams;


public class QRCard extends Activity implements ViewFactory{
	
	private int CurrentPosition;
	
	private String path;
	private Uri uri;
	private File file;
	
	private Gallery gallery;
	private ImageView imageview;
	private Bitmap bm;
	private Button share,delete;
	private ImageAdapter adapter;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	
        setContentView(R.layout.activity_qrcard);
        
        imageview=(ImageView)findViewById(R.id.qrcardImage);
        
        gallery=(Gallery)findViewById(R.id.gallery);
        
        adapter=new ImageAdapter(this,getImagePathFromSD());
        
        gallery.setAdapter(adapter);
        
        
        gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

        	public  void  onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        		
        		CurrentPosition=position;
        		
        		path=getImagePathFromSD().get(position).toString();
        		
        		file=new File(path);
        		
        		bm = BitmapFactory.decodeFile(path);
        		
        		imageview.setImageBitmap(bm);
        
        	}
            
            
            public void onNothingSelected(AdapterView<?> arg0) {
            	
            	}
            
         });
        
       
        
        
        share=(Button)findViewById(R.id.share);
        
        share.setOnClickListener(new Button.OnClickListener(){
        	
        	public void onClick(View view){
        		
        		if(file==null){
        			
        			 Toast.makeText(QRCard.this, "名片夹为空！",
     	                    Toast.LENGTH_SHORT).show();
        			
        		}
        		else{
        		
        		uri=Uri.fromFile(file);
        		
        		Intent intent=new Intent(Intent.ACTION_SEND);   
        		intent.setType("image/*");
        		intent.putExtra(Intent.EXTRA_STREAM, uri);
        		intent.putExtra(Intent.EXTRA_TEXT, "我的二维码名片：");    
        		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);   
            	
        		startActivity(Intent.createChooser(intent,"分享我的QRCode名片"));  
        		}
        		
        	}
        });
        
        
        delete=(Button)findViewById(R.id.delete);
        
        delete.setOnClickListener(new Button.OnClickListener(){
        	
        	public void onClick(View view){
        		
        		Builder alertDialog = new AlertDialog.Builder(QRCard.this). 
                        setTitle("确定删除？"). 
                        setMessage("您确定删除当前名片吗？"). 
                        setIcon(R.drawable.ic_launcher). 
                        setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                             
                           
                            public void onClick(DialogInterface dialog, int which) { 
                            	
                            	
                        		if(file==null){
                        			
                        			 Toast.makeText(QRCard.this, "名片夹为空！",
                     	                    Toast.LENGTH_SHORT).show();
                        			
                        		}
                        		else{
                        			
                        		
                            	
                            	file.delete();
                            	
                            	List<String> list=getImagePathFromSD();
                            	
                            	adapter=new ImageAdapter(QRCard.this,list);
                            	
                                gallery.setAdapter(adapter);
                            	
                            	
                            	int size=list.size();
                            	
                            	
                            	
                            	if(CurrentPosition==size)
                            	{
                            		CurrentPosition=CurrentPosition-1;
                            	}
                            	
                            	if(size!=0){
                           
    
                            	
                            	path=getImagePathFromSD().get(CurrentPosition).toString();
                            	
                            	bm = BitmapFactory.decodeFile(path);
                        		
                        		imageview.setImageBitmap(bm);
                            	}
                            	else{
                            		imageview.setImageBitmap(null);
                            	}
                           
                            	
                        		}
                            	
                            	
                            } 
                        }). 
                        setNegativeButton("取消", new DialogInterface.OnClickListener() { 
                             
                           
                            public void onClick(DialogInterface dialog, int which) { 
                                
                            } 
                        });
         		alertDialog.show(); 
         	
        		
        		
        		
        	}
        });
    }
        
 

	
	public View makeView() {
			ImageView imageView = new ImageView(this);
			imageView.setBackgroundColor(0xFF000000);
			imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
			imageView.setLayoutParams(new ImageSwitcher.LayoutParams(
			LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return imageView;
		}
		
	
    private List<String> getImagePathFromSD() {
        
	     List<String> it = new ArrayList<String>();
		       
		 String imagePath = Environment.getExternalStorageDirectory().toString()+"/QRCard";
		 
		 File mFile = new File(imagePath);
		 File[] files = mFile.listFiles();
		 if(files==null){
			 Toast.makeText(QRCard.this, "名片夹为空！",
	                    Toast.LENGTH_SHORT).show();
			 
		 }

		 for (int i = 0; i < files.length; i++) {
			   
			 File file = files[i];
		     if (checkIsImageFile(file.getPath()))
		                it.add(file.getPath());
		        }
		
		return it;
	}
	

	
	private boolean checkIsImageFile(String fName) {
		        
		boolean isImageFormat;
	
		String end = fName
	                .substring(fName.lastIndexOf(".") + 1, fName.length())
	                .toLowerCase();
	 
		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
	                || end.equals("jpeg") || end.equals("bmp")) {
	            isImageFormat = true;
		        } else {
		            isImageFormat = false;
	        }
	        return isImageFormat;
		    }
	
}
