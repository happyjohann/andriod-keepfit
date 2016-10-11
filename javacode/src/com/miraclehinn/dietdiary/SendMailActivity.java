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
				String emailSubject = "�û�����";
				String emailBody = sendMailInfoEditText.getText().toString();
							
				 //�����ʼ�Ĭ�ϵ�ַ
				String[] emailReciver = new String[]{"miraclehinn@gmail.com", "miraclehinn@163.com"};
				email.putExtra(Intent.EXTRA_EMAIL, emailReciver);
				//�����ʼ�Ĭ�ϱ���
				
				email.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
				//����ҪĬ�Ϸ��͵�����
				
				email.putExtra(Intent.EXTRA_TEXT, emailBody);
				//����ϵͳ���ʼ�ϵͳ
				email.setType("plain/text");
				startActivity(Intent.createChooser(email, "��ѡ���ʼ��������"));//http://www.eoeandroid.com/thread-74388-1-1.html,http://blog.csdn.net/lichaoandy/article/details/5649163
				
				
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
