package com.example.neha.textmessage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.TextView;
import android.widget.Toast;


public class TextMessageCode extends AppCompatActivity {

    BroadcastReceiver broadcast;
    Bundle bundle;
    Object[] extra;
    SmsMessage[] messages;
    TextView showState;
    int state;
    TextView sms;
    final SmsManager sender=SmsManager.getDefault();
    boolean start;
    boolean start2;
    int times=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_message_code);
        start=true;
        start2=true;
        state=1;

        TextView showState=(TextView)findViewById(R.id.textView);
        showState.setText("State: 1");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS}, 1); //IDK WHAT THE ONE DOES
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 1); //IDK WHAT THE ONE DOES
        }

        bundle=new Bundle();
        sms=(TextView)findViewById(R.id.tv);

        broadcast= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                bundle= intent.getExtras();
                extra=(Object[])bundle.get("pdus");
                messages=new SmsMessage[extra.length];
                for (int i=0; i<extra.length; i++){
                    byte[] f = (byte[]) extra[i];
                    messages[i]=SmsMessage.createFromPdu(f, intent.getStringExtra("format"));
                    sms.setText(messages[i].getMessageBody());
                }
                Handler delay=new Handler();
                delay.postDelayed(sendRunnable(sms.getText()+""), 3000);
            }
        };
        registerReceiver(broadcast, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
    }

    public Runnable sendRunnable(String body){
        TextView showState=(TextView)findViewById(R.id.textView);
        body=body.toLowerCase();
        String message="What do you mean?";
        if (start2) {
            if ((body.contains("hi")) || (body.contains("hey")) || (body.contains("what's up?")) || (body.contains("hello"))||(body.contains("how are you"))) {
                state = 1;
            }
        }
        //Toast.makeText(getApplicationContext(), state+"", Toast.LENGTH_SHORT).show();
        showState.setText("State: "+String.valueOf(state));
        int r=(int)(Math.random()*4)+1;
        String one="Hey..";
        String two="Hi, how's it going?";
        String three="Hello";
        String four="What's up with you?";
        String five="Listen, we need to talk. I think we need to break up.";
        String six="I have something to tell you about. I think we need to break up.";
        String seven="Can we talk about something? I think we need to break up.";
        String eight="I need to tell you something. I think we need to break up.";
        String nine="I'm sorry, this is too much right now. Bye.";
        String ten="It's too hard to talk to you right now. Goodbye.";
        String eleven="I have to go this is too hard. I guess I'll see you around";
        String twelve="This is too hard. I must go now";
        if (state==2){
            times++;
            //Toast.makeText(getApplicationContext(), times+"", Toast.LENGTH_SHORT).show();
            if (start){
                if(r==1){
                    message=five;
                } else if(r==2){
                    message=six;
                } else if(r==3){
                    message=seven;
                } else {
                    message=eight;
                }
            } else {
                if (times<=4) {
                    if ((body.contains("now"))){
                        message="I hope we can stay friends";
                    } else if ((body.contains("when")) || (body.contains("long"))) {
                        message = "I've been thinking about this for a couple weeks.";
                    } else if ((body.contains("why")) || (body.contains("how")) || (body.contains("what"))) {
                        int r2 = (int) (Math.random() * 5) + 1;
                        String a = "It's not you it's me";
                        String b = "I think we are better as friends";
                        String c = "There's no spark anymore";
                        String d = "I don't love you like that anymore";
                        String e = "We have been fighting way too much";
                        if (r2 == 1) {
                            message = a;
                        } else if (r2 == 2) {
                            message = b;
                        } else if (r2 == 3) {
                            message = c;
                        } else if (r2 == 4) {
                            message = d;
                        } else message = e;
                    }
                } else state=3;
            }
            start=false;
        } else if (state==3){
            if(r==1){
                message=nine;
            } else if(r==2){
                message=ten;
            } else if(r==3){
                message=eleven;
            } else {
                message=twelve;
            }
        } else {
            if(r==1){
                message=one;
            } else if(r==2){
                message=two;
            } else if(r==3){
                message=three;
            } else {
                message=four;
            }
            state=2;
            start2=false;
        };
        final String it=message;
        Runnable gogo=new Runnable() {
            @Override
            public void run() {
                sender.sendTextMessage(messages[0].getOriginatingAddress(), null, it, null, null);
            }
        };
        return gogo;
    }
}




