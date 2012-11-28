package com.shining.qrcode;

import android.content.Context;   
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.GridView;  
import android.widget.ImageView;  

public class ImageAdapterForTab extends BaseAdapter {  
    private Context mContext;   
    private ImageView[] imgItems;  
    private int selResId;  
    public ImageAdapterForTab(Context c,int[] picIds,int width,int height,int selResId) {   
        mContext = c;   
        this.selResId=selResId;  
        imgItems=new ImageView[picIds.length];  
        for(int i=0;i<picIds.length;i++)  
        {  
            imgItems[i] = new ImageView(mContext);   
            imgItems[i].setLayoutParams(new GridView.LayoutParams(width, height)); 
            imgItems[i].setAdjustViewBounds(false);   
            imgItems[i].setPadding(2, 2, 2, 2);   
            imgItems[i].setImageResource(picIds[i]);   
        }  
    }   
   
    public int getCount() {   
        return imgItems.length;   
    }   
   
    public Object getItem(int position) {   
        return position;   
    }   
   
    public long getItemId(int position) {   
        return position;   
    }   
   
    
    public void SetFocus(int index)    
    {    
        for(int i=0;i<imgItems.length;i++)    
        {    
            if(i!=index)    
            {    
                imgItems[i].setBackgroundResource(0); 
            }    
        }    
        imgItems[index].setBackgroundResource(selResId);
    }    
      
    public View getView(int position, View convertView, ViewGroup parent) {   
        ImageView imageView;   
        if (convertView == null) {   
            imageView=imgItems[position];  
        } else {   
            imageView = (ImageView) convertView;   
        }   
        return imageView;   
    }   
}   