package com.shining.qrcode;


import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MPagerAdapter extends PagerAdapter {

	private List<Integer> mPaths;  
	private  Bitmap bm;
	private  ImageView iv;
	
    
    private Context mContext;  
      
    public MPagerAdapter(Context cx) {  
        mContext = cx.getApplicationContext();  
    }  
      
    public void change(List<Integer> paths) {  
        mPaths = paths;  
    }  
      
     
    public int getCount() {  
      
        return mPaths.size();  
    }  
  

    public boolean isViewFromObject(View view, Object obj) {  
  
        return view == (View) obj;  
    }  
  

    public Object instantiateItem (ViewGroup container, int position) {  
        iv = new ImageView(mContext);  
        try {  
            //Bitmap bm = BitmapFactory.decodeFile(getResource(),mPaths.get(position));//载入bitmap  
        	bm=BitmapFactory.decodeResource(mContext.getResources(), mPaths.get(position));
            iv.setImageBitmap(bm);  
        } catch (OutOfMemoryError e) {  
          
            e.printStackTrace();  
        }  
        ((ViewPager)container).addView(iv, 0);  
        return iv;  
    }  

    public void destroyItem (ViewGroup container, int position, Object object) {  
        container.removeView((View)object);  
    }  
    
    public  Bitmap getBitmap(){
    	
    	
    	return bm;
    }
    
    public  void setBitmap(Bitmap b){
    	
    	 iv.setImageBitmap(b);  
    	
    }
	
}
