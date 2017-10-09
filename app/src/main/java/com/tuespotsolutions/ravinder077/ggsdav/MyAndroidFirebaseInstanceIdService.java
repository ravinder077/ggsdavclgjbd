package com.tuespotsolutions.ravinder077.ggsdav;

/**
 * Created by ravinder077 on 24-09-2017.
 */

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAndroidFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "MyAndroidFCMIIDService";

    @Override
    public void onTokenRefresh() {
        //Get hold of the registration token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        //Log the token
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token) {


        try {

            System.err.print("token is "+"http://timepasstoday.com/addtoken.php?id="+token);
            URL url=new URL("http://timepasstoday.com/addtoken.php?id="+token);
            System.err.println("url"+url);
            HttpURLConnection con=(HttpURLConnection) url.openConnection();
            con.setRequestProperty("User-Agent","");
            con.setRequestMethod("GET");

            con.connect();

            InputStream in= con.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(in));
            StringBuilder result=new StringBuilder();
            String line;
            while((line=reader.readLine())!=null)
            {
                result.append(line);
            }

            System.err.print(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }



        //Implement this method if you want to store the token on your server
    }
}