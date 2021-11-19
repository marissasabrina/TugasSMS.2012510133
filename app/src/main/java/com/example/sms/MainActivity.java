package com.example.sms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonsend;
    private EditText messagecontent, phonenumber;

    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonsend = (Button) findViewById(R.id.idbtnSend);
        messagecontent = (EditText) findViewById(R.id.idTxtMsg);
        phonenumber = (EditText) findViewById(R.id.idTxtPhoneNum);
        sendSMS();
    }

    protected void sendSMS() {
        buttonsend.setOnClickListener((v) -> {
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);

            if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
                MessageContent();
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.SEND_SMS},MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        });
    }

    public void MessageContent() {
        String myNumber = phonenumber.getText().toString().trim();
        String myMessage = messagecontent.getText().toString().trim();
        if (myNumber==null || myNumber.equals("") || myMessage==null || myMessage.equals("")) {
            Toast.makeText(this, "Message can't be empty", Toast.LENGTH_SHORT).show();
        } else {
            if (TextUtils.isDigitsOnly(myNumber)) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(myNumber, null, myMessage, null, null);
                Toast.makeText(this, "Sending message", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Only input number",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult (int requestCode, String[] permissions, int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if (grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED) {
                    MessageContent();
                } else {
                    Toast.makeText(this,"Please give access first", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void Move (View view) {
        Intent intent = new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    public void sendmsg(View view) {
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }
}