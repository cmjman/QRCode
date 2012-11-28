package com.shining.qrcode;

import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	
	int mGalleryItemBackground;
	private Context mContext;
	private List<String> lis;
	
	public ImageAdapter(Context c, List<String> li) {
		
		mContext = c;
	    lis = li;

	    TypedArray mTypeArray = c.obtainStyledAttributes(R.styleable.Gallery);
	    
	    mGalleryItemBackground = mTypeArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);

	    mTypeArray.recycle();
    }

	public int getCount() {
		
		return lis.size();
	}
	
	public Object getItem(int position) {
		
		return position;
	        
	}

	public long getItemId(int position) {
	          
		return position;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {

	    ImageView i = new ImageView(mContext);
	
		Bitmap bm = BitmapFactory.decodeFile(lis.get(position).toString());
		
		i.setImageBitmap(bm);

	    i.setScaleType(ImageView.ScaleType.FIT_CENTER);

	    i.setLayoutParams(new Gallery.LayoutParams(320, 240));

	    i.setBackgroundResource(mGalleryItemBackground);
	
		return i;
	}
}
