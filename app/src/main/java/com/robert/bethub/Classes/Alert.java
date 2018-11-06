package com.robert.bethub.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.view.View;

import com.robert.bethub.View.MembershipActivity;

public class Alert{

    public Context mContext;
    public Alert(Context context){
        this.mContext = context;
    }


    public static void showAlert(Context context, String messageString) {
        String title = "BetHub";
        String message = messageString;
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                alertDialog.dismiss();
            }
        });
        alertDialog.show();


    }

    public static void showAlertWithAction(final Context context, String messageString, final Class<? extends Activity> activity) {
        String title = "BetHub";
        String message = messageString;

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(context,activity);
                intent.putExtra("LoginSuccess",true);
                context.startActivity(intent);

                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }
}
