package com.kmn.servicereceivernotification;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CountService extends Service {
    private static final String TAG = CountService.class.getSimpleName() ;
    public static final String TIME = "TIME";
    private ScheduledExecutorService mScheduledExecutorService ;
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
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                long currentTimeMills = System.currentTimeMillis();
                Log.d(TAG, "run: " + currentTimeMills); // task
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

    @Override
    public void onDestroy() {
        mScheduledExecutorService.shutdownNow(); // Now - потому если мы нажнем на кнопку stopService то сразу же отключает
        // выводит логи
        Log.d(TAG, "onDestroy: ");
    }
}