package com.robert.bethub;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.robert.bethub.Classes.Alert;
import com.robert.bethub.View.Login;
import com.robert.bethub.View.MainActivity;
import com.robert.bethub.View.NotificationContentActivity;
import com.robert.bethub.View.signUp;

import org.json.JSONObject;

import eu.amirs.JSON;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "Tag";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post("https://bethub.pro/pnfw/register")
                //.addBodyParameter(user)
                .addUrlEncodeFormBodyParameter("token",token)
                .addUrlEncodeFormBodyParameter("os","Android")
                .setTag("authentication")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("tag","sucess");

                        //Log.d("tag1","status is : " + json.key("status").stringValue());

                        // do anything with response
                    }
                    @Override
                    public void onError(ANError error) {
                        Log.d("tag","this is the error"+error.getErrorCode());
                        // handle error
                    }
                });

    }



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        //sendNotification(remoteMessage.getNotification().getTitle());
        // Check if message contains a data payload.

        if (remoteMessage.getData().size() > 0) {

            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Intent intent = new Intent(this, NotificationContentActivity.class);
            intent.putExtra("id",remoteMessage.getData().get("id"));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.bethublogo)
                    .setPriority(Notification.PRIORITY_MAX)
                    .setContentTitle("BetHub")
                    .setContentText(remoteMessage.getNotification().getTitle()).
                            setDefaults(Notification.DEFAULT_ALL).
                            setAutoCancel(true).
                            setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());


        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }


}