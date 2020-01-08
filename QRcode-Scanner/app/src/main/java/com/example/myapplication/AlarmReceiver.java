package com.example.myapplication;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import static com.example.myapplication.MainActivity.NOTIFICATION_CHANNEL_ID ;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    // private static final int NOTIFICATION_ID = 0;
    static final String ACTION_SNOOZE = "OK";
    static final String EXTRA_NOTIFICATION_ID = "notification-id";

    private static final String TAG = "receiver";
    int i = 0;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    public static String NOTIFICATION_ID = "notification-id" ;
    public static String NOTIFICATION = "notification" ;



    @Override
    public void onReceive(Context context, Intent intent) {
        // Toast.makeText(context, "Good John", Toast.LENGTH_LONG).show();
        Toast toast = Toast.makeText(context,"Good John", Toast.LENGTH_LONG);
        Date dat = new Date();
        Log.i("A check times",">>> "+dat);
        i++;
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

        if (ACTION_SNOOZE.equals(intent.getAction())) {
            int notificationId = intent.getExtras().getInt(EXTRA_NOTIFICATION_ID);
            Log.e(TAG, "Cancel notification with id " + notificationId);
            NotificationManager notificationmanager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
            notificationmanager.cancel(notificationId);
        }
        //
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context. NOTIFICATION_SERVICE ) ;
        Notification notification = intent.getParcelableExtra( NOTIFICATION ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notificationChannel) ;
        }
        int id = intent.getIntExtra( NOTIFICATION_ID , 0 ) ;
        assert notificationManager != null;
        // NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("開獎提醒")
                .setContentText("AOS high pass")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("pinu defined network"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(id , builder.build()) ;

        //
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
