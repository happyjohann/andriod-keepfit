package com.miraclehinn.dietdiary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SendMailActivity extends Activity {

	private EditText sendMailInfoEditText;
	private Button sendMailSendButton;
	private Button sendMailCancelButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.send_mail);
		
		sendMailInfoEditText = (EditText) findViewById(R.id.SendMailInfoEditText);
		sendMailSendButton = (Button)findViewById(R.id.SendMailSendButton);
		sendMailCancelButton = (Button)findViewById(R.id.SendMailCancelButton);
		
		sendMailSendButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent email=new Intent(Intent.ACTION_SEND); 
				String emailSubject = "用户反馈";
				String emailBody = sendMailInfoEditText.getText().toString();
							
				 //设置邮件默认地址
				String[] emailReciver = new String[]{"miraclehinn@gmail.com", "miraclehinn@163.com"};
				email.putExtra(Intent.EXTRA_EMAIL, emailReciver);
				//设置邮件默认标题
				
				email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
				//设置要默认发送的内容
				
				email.putExtra(Intent.EXTRA_TEXT, emailBody);
				//调用系统的邮件系统
				email.setType("plain/text");
				startActivity(Intent.createChooser(email, "请选择邮件发送软件"));//http://www.eoeandroid.com/thread-74388-1-1.html,http://blog.csdn.net/lichaoandy/article/details/5649163
				
				
//				email.setData(Uri.parse("mailto:miraclehinn@163.com")); 
//				email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);  
//				email.putExtra(Intent.EXTRA_TEXT, emailBody);  
//				startActivity(email);
			}
		});
		
		sendMailCancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SendMailActivity.this.finish();	
			}
		});
	}
}
