package com.shining.qrcode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.text.ClipboardManager;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DecodeResult extends Activity{
	
	private Button save,copy;
	private TextView _name,_phone,_email,_qq;
	private String strQR;
	private String name,phone,email,qq,infos[];
	
	public void onCreate(Bundle savedInstanceState) {
        
		super.onCreate(savedInstanceState);
		
		Bundle bundle=getIntent().getExtras();
	
		strQR=(String)bundle.getString("qrcode");
        
		setContentView(R.layout.activity_decoderesult);
		
		if(!getInfo())
		{
			if(strQR!="")
			{
				if(URLUtil.isNetworkUrl(strQR))
				{
					mMakeTextToast(strQR,true);
					Uri uri=Uri.parse(strQR);
					Intent intent=new Intent(Intent.ACTION_VIEW,uri);
					startActivity(intent);
				}
				else if(eregi("wtai://",strQR))
				{
					String[] aryTemp=strQR.split("wtai://");
					Intent mIntentDial =new Intent(
					"android.intent.action.CALL",Uri.parse("tel:"+aryTemp[1]));
					startActivity(mIntentDial);
				}
				else if(eregi("TEL:",strQR))
				{
					String[] aryTemp=strQR.split("TEL:");
					Intent mIntentDial=new Intent(
					"android.intent.action.CALL",Uri.parse("tel:"+aryTemp[1]));
					startActivity(mIntentDial);
				}
				else
				{
					mMakeTextToast("解析到的内容不符合MECard格式，具体内容如下：\n"+strQR,true);
				}
			}
		
		}
		
		_name=(TextView)findViewById(R.id._name);
		_name.setText("姓名："+name);
		
		_email=(TextView)findViewById(R.id._email);
		_email.setText("邮箱："+email);
		
		_qq=(TextView)findViewById(R.id._qq);
		_qq.setText("QQ："+qq);
		

		_phone=(TextView)findViewById(R.id._phone);
		_phone.setText("手机："+phone);
		
		copy=(Button)findViewById(R.id.copyToClipboard);
		
		copy.setOnClickListener(new Button.OnClickListener(){
			
			public void onClick(View view){
				copyToClipboard();
			}
		});
		
		
		save=(Button)findViewById(R.id.saveToContact);
	
		save.setOnClickListener(new Button.OnClickListener(){
     	
     	public void onClick(View view){
     	
     		Builder alertDialog = new AlertDialog.Builder(DecodeResult.this)
     				.setTitle("确定添加？")
     				.setMessage("您确定添加该联系人到通讯录吗？")
                    .setIcon(R.drawable.ic_launcher)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() { 
                         
                       
                        public void onClick(DialogInterface dialog, int which) { 
                    		saveToContact();
                        } 
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() { 
                         
                       public void onClick(DialogInterface dialog, int which) { 
                            
                        } 
                    });
     		alertDialog.show(); 
     	
     		}
		});
	}
	
	
	private boolean getInfo(){
		
		infos=strQR.split(";");
		
		for(String info:infos){
			if(eregi("MECARD:N:",info))
				name=info.replaceAll("MECARD:N:", "");
			else if(eregi("EMAIL:",info))
				email=info.replaceAll("EMAIL:", "");
			else if(eregi("TEL:",info))
				phone=info.replaceAll("TEL:", "");
			else if(eregi("QQ:",info))
				qq=info.replaceAll("QQ:", "");
		}
		
		if(name!=null)
			return true;
		return false;
	}
	
	
	private void saveToContact(){
		
		ContentValues values = new ContentValues();  
       
        Uri rawContactUri = this.getContentResolver().insert(RawContacts.CONTENT_URI, values);  
        long rawContactId = ContentUris.parseId(rawContactUri);  
      
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactId);   
        values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE); 
        values.put(StructuredName.GIVEN_NAME,name.toString());  
        this.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);  
       
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactId);  
        values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);  
        values.put(Phone.NUMBER,phone.toString());  
        values.put(Phone.TYPE, Phone.TYPE_MOBILE);  
        this.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);  
      
        values.clear();  
        values.put(Data.RAW_CONTACT_ID, rawContactId);  
        values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);  
        values.put(Email.DATA,email.toString());  
        values.put(Email.TYPE, Email.TYPE_WORK);  
        this.getContentResolver().insert(android.provider.ContactsContract.Data.CONTENT_URI, values);
        
    	Toast.makeText(DecodeResult.this, "已成功保存到手机通讯录", Toast.LENGTH_LONG).show();
		
	}
	
	private void copyToClipboard(){
		
		ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

		clip.setText(strQR); 
		
		Toast.makeText(DecodeResult.this, "已成功复制文本到剪贴板", Toast.LENGTH_LONG).show();
		
	}
	
	public void mMakeTextToast(String str,boolean isLong){
		if(isLong==true){
			Toast.makeText(DecodeResult.this, str, Toast.LENGTH_LONG).show();
		}
		else
		{
			Toast.makeText(DecodeResult.this, str, Toast.LENGTH_SHORT).show();
		}
	}
	


	public static boolean eregi(String strPat,String strUnknow){
	
		String strPattern="(?i)"+strPat;
		
		Pattern p=Pattern.compile(strPattern);
		Matcher m=p.matcher(strUnknow);
		return m.find();
	}
}
