package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import java.util.Date;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    // private static final int NOTIFICATION_ID = 0;
    static final String ACTION_SNOOZE = "OK";
    static final String EXTRA_NOTIFICATION_ID = "notification-id";

    private static final String TAG = "receiver";
    int i = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        // Toast.makeText(context, "Good John", Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(context,"Good John", Toast.LENGTH_LONG);
        Date dat = new Date();
        Log.i("A check times",">>> "+dat);
        i++;
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        //toast.setGravity(Gravity.CENTER, 0, 0);
        /*
        try { //部分設備不允許此作法，因此要用 try..catch包起來

            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(android.content.Context.NOTIFICATION_SERVICE);
            Notification notification = new Notification();
            // 會有通知預設的鈴聲、振動、light
            notification.defaults = Notification.DEFAULT_ALL;
            notificationManager.notify(0, notification);

        } catch (Exception e) {

        }

        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
         */
    }

}
