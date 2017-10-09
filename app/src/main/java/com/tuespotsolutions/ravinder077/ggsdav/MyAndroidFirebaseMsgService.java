package com.tuespotsolutions.ravinder077.ggsdav;

/**
 * Created by ravinder077 on 24-09-2017.
 */

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyAndroidFirebaseMsgService extends FirebaseMessagingService {
    private static final String TAG = "MyAndroidFCMService";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //Log data to Log Cat
      /*  Log.d(TAG, "From: " + remoteMessage.getFrom());
        Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        //create notification

        remoteMessage.getData().get("message");
        remoteMessage.getData().get("title");
        remoteMessage.getData().get("subtitle");

        System.err.println("message " +remoteMessage.getData().get("message"));
        System.err.println("title " +remoteMessage.getData().get("title"));
        System.err.println("subtitle "+remoteMessage.getData().get("subtitle"));*/
        //onMessageReceived will be called when ever you receive new message from server.. (app in background and foreground )
        Log.d("FCM", "From: " + remoteMessage.getFrom());

        if(remoteMessage.getNotification()!=null){

            try {

                Log.d("FCM", "Notification Message Body: " + remoteMessage.getNotification().getBody());


               createNotification(remoteMessage.getNotification().getBody());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if(remoteMessage.getData().containsKey("post_id") && remoteMessage.getData().containsKey("post_title")){

            try {
                Log.d("Post ID", remoteMessage.getData().get("post_id").toString());
                Log.d("Post Title", remoteMessage.getData().get("post_title").toString());

                createNotification(remoteMessage.getData().get("post_title").toString());
                // eg. Server Send Structure data:{"post_id":"12345","post_title":"A Blog Post"}
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }

    }

    private void createNotification( String messageBody) {
        Intent intent = new Intent( this , MainActivity. class );
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent resultIntent = PendingIntent.getActivity( this , 0, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri notificationSoundURI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder( this)
                .setSmallIcon(R.drawable.a)
                .setContentTitle("New Message Received ")
                .setContentText(messageBody)
                .setAutoCancel( true )

                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(notificationSoundURI)
                .setContentIntent(resultIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, mNotificationBuilder.build());
    }
}