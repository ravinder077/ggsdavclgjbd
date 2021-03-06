package com.tuespotsolutions.ravinder077.ggsdav;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

public class ResendOtp extends AppCompatActivity {
    String sotp=null;
    String mobno=null;
    String sname=null;
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private TextView textView;
    private Handler handler = new Handler();
    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resendotp);


        // Prompt for read contacts Permission start

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }


            else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);


            }
        }

        // Prompt for read contacts Permission end

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Start long running operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;

                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus);

                        }
                    });
                    try {
                        // Sleep for 300 milliseconds.
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        final EditText etmob=(EditText) findViewById(R.id.etmob);
        final Button btncont=(Button) findViewById(R.id.btncont);
        btncont.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(etmob.getText().toString().length()<4)
                {
                    etmob.setError("Invalid OTP, Check your inbox and try again");
                }
                else
                {

                    Intent intent=getIntent();
                    if(flag){

                    }
                    else {
                        sotp = intent.getStringExtra("sotp");
                        mobno=intent.getStringExtra("mobno");
                        sname=intent.getStringExtra("sname");

                    }
                    String mobno=intent.getStringExtra("mobno");
                    System.err.println("mobile" + mobno);
                    // Toast.makeText(otp.this, sotp, Toast.LENGTH_SHORT).show();
                    if(etmob.getText().toString().equals(sotp))
                    {

                        OtpGen udata=new OtpGen();
                        String numurl="http://omtii.com/dav/app/sturegister.php?name=" + sname + "&mobile=" + mobno;
                        udata.execute(numurl);

                        SQLiteDatabase mydata = null;
                        mydata = openOrCreateDatabase("dav", MODE_PRIVATE, null);

                        mydata.execSQL("create table if not exists stureg(sname varchar,mobile varchar);");

                        mydata.execSQL("insert into stureg values ('" +sname+ "','" +mobno+ "')");

                        Intent i = new Intent(v.getContext(), MainActivity.class); // change
                        i.putExtra("mobno",mobno);
                        startActivity(i);
                    }
                    else
                    {
                        etmob.setError("Invalid OTP");
                    }
                }
            }
        });

        final Button btnresend=(Button) findViewById(R.id.btnresend);
        btnresend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent=getIntent();
                String mobno=intent.getStringExtra("mobno");

                System.err.println("in Resend button click ");

                OtpGen otpg=new OtpGen();
                String numurl="http://omtii.com/mile/otpgen.php?id="+mobno;

                System.err.println("numurl"+numurl);

                otpg.execute(numurl);
                String st=null;
                try {
                    st=otpg.get();
                    sotp=st;
                    flag=true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                OtpGen otpg1=new OtpGen();
                String numur2="http://omtii.com/dav/app/otpapi.php?mob="+mobno+"&otp="+st;

                System.err.println("numurl"+numur2);

                otpg1.execute(numur2);/*
                Intent i = new Intent(v.getContext(), otp.class);
                i.putExtra("sotp",st);
                i.putExtra("mobno",mobno);
                startActivity(i);*/
                //Toast.makeText(login.this, st, Toast.LENGTH_SHORT).show();

                //  SmsManager sms = SmsManager.getDefault();
                // String mess="Your One Time Password (OTP) for Direct Message is " + st + ". Only valid for 20 min.";

                //String mobno=etmob.getText().toString();
                //  sms.sendTextMessage(mobno, null,mess , null, null);

               /* Intent i = new Intent(v.getContext(), otp.class);
                i.putExtra("sotp",st);
                i.putExtra("mobno",mobno);
                startActivity(i);*/
            }
        });
    }
}

