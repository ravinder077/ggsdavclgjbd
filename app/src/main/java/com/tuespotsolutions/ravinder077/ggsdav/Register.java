package com.tuespotsolutions.ravinder077.ggsdav;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

/**
 * Created by Chugh on 9/24/2017.
 */

public class Register extends AppCompatActivity {

    private int MY_PERMISSIONS_REQUEST_SEND_SMS;
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.register);


        SQLiteDatabase mydata=openOrCreateDatabase("dav",MODE_PRIVATE,null);
        mydata.execSQL("create table if not exists stureg(sname varchar,mobile varchar);");
        Cursor resultSet = mydata.rawQuery("Select * from stureg", null);
        if(resultSet.getCount()>0) {
            resultSet.moveToFirst();
            String name = resultSet.getString(0);
            String mnum = resultSet.getString(1);


            if (mnum != null) {
                this.finish();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);  // change
                i.putExtra("name",name);
                i.putExtra("mobile",mnum);
                startActivity(i);
            }

        }

        mydata.close();
        resultSet.close();
        // Prompt for send sms Permission start

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            }


            else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_SEND_SMS);


            }
        }

        // Prompt for send sms Permission end


       final  EditText etname=(EditText) findViewById(R.id.etname);
       final  EditText etmob=(EditText) findViewById(R.id.etmob);
        final Button reg=(Button) findViewById(R.id.btnsotp);
        reg.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                // Code here executes on main thread after user presses button
                if(etname.getText().toString().length()<3)
                {
                    etname.setError("Please Enter Valid Name");

                }
                else if(etmob.getText().toString().length()<10)
                {
                    etmob.setError("Mobile Number must be 10 digits");
                }

                else

                {
                    OtpGen otpg=new OtpGen();
                    String numurl="http://omtii.com/dav/app/otpgen.php?id="+etmob.getText();


                    System.err.println("numurl"+numurl);

                    otpg.execute(numurl);
                    String st=null;
                    try {
                        st=otpg.get();
                        System.err.println("Otp=" + st);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(login.this, st, Toast.LENGTH_SHORT).show();

                    SmsManager sms = SmsManager.getDefault();
                    String mobno=etmob.getText().toString();
                    String sname=etname.getText().toString();
                    if(st==null)
                    {
                       // String mess="Please Connect your Device with Internet";
                        //sms.sendTextMessage(mobno, null,mess , null, null);

                        // Toast.makeText(login.this, "Please Wait", Toast.LENGTH_SHORT).show();
                        etmob.setError("Please check your network connection and try again");

                    }
                    else {

                    //String mess = "Your One Time Password (OTP) for Direct Message is " + st + ". Only valid for 20 min.";
                    // sms.sendTextMessage(mobno, null,mess , null, null);

                       OtpGen otpg1=new OtpGen();
                        String numur2="http://omtii.com/dav/app/otpapi.php?mob="+mobno+"&otp="+st;

                        System.err.println("numurl"+numur2);

                        otpg1.execute(numur2);

                        Intent i = new Intent(v.getContext(), ResendOtp.class);
                        i.putExtra("sotp",st);
                        i.putExtra("mobno",mobno);
                        i.putExtra("sname",sname);
                        startActivity(i);
                    }

                }

                         }

        });




    }







}
