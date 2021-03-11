package com.kmn.servicereceivernotification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {
    private static final String TAG = CountService.class.getSimpleName() ;
    public static final String TIME = "TIME";
    private ScheduledExecutorService mScheduledExecutorService ;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;

    public CountService() {

    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        mScheduledExecutorService =  Executors.newScheduledThreadPool(1);

        mManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        mBuilder = getNotificationBuilder();
        mBuilder.setContentTitle("Count service notification")
                .setSmallIcon(R.drawable.ic_launcher_foreground);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");

        startForeground(24, getNotification("start notification"));

        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentTimeMills = System.currentTimeMillis();
                Log.d(TAG, "run: " + currentTimeMills); // task
                mManager.notify(24, getNotification("time is " + currentTimeMills));
                Intent intentToSend = new Intent(SimpleReceiver.SIMPLE_ACTION);
                intentToSend.putExtra(TIME, currentTimeMills);
                sendBroadcast(intentToSend);
                /*
                мы каждые 4000 миллисекунд создаем Intent, оборачиваем туда текущие значения миллисекунд и отправляем в Broadcast.
                 */
            }
        }, 1000,4000, TimeUnit.MILLISECONDS );

        return START_STICKY; //  start_sticky - означает, что если сервис был убит системой,
        // то она постарается его восстановить, как только у нее появятся для этого ресурсы.
    }

    private Notification getNotification(String contentText){

        Intent intent = new Intent(this, TempActivity.class);
        intent.putExtra(TIME, contentText);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        return mBuilder.setContentText(contentText)
                .setContentIntent(pendingIntent)
                .build();
    }


    @Override
    public void onDestroy() {
        mScheduledExecutorService.shutdownNow(); // Now - потому если мы нажнем на кнопку stopService то сразу же отключает
        // выводит логи
        Log.d(TAG, "onDestroy: ");
    }

    public NotificationCompat.Builder getNotificationBuilder() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            return new NotificationCompat.Builder(this);
        }else {
            String channelID = "my_channel_id";
            if(mManager.getNotificationChannel(channelID) == null){
                NotificationChannel notificationChannel = new NotificationChannel(channelID, "Text for user", NotificationManager.IMPORTANCE_LOW);
                mManager.createNotificationChannel(notificationChannel);
            }
            return new NotificationCompat.Builder(this, channelID);
        }
    }

}
