package com.robert.bethub;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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
        showNotification(remoteMessage.getNotification().getTitle());
    }


    public void showNotification(String message) {
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        Notification notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.bethublogo)
                .setContentTitle("BetHub")
                .setContentText(message)
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}