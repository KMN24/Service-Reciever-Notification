package com.kmn.servicereceivernotification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mStartServiceBtn;
    private Button mStopServiceBtn;
    private Button mSendBroadcastBtn;
    SimpleReceiver mSimpleReceiver;
    private IntentFilter mIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStartServiceBtn = findViewById(R.id.start_service);
        mStopServiceBtn = findViewById(R.id.stop_service);
        mSendBroadcastBtn = findViewById(R.id.button_send_broadcast);

        mStartServiceBtn.setOnClickListener(new View.OnClickListener() { // Запускаем сервис
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountService.class);
                startService(intent);

            }
        });

        mStopServiceBtn.setOnClickListener(new View.OnClickListener() { // Отключаем сервис
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountService.class);
                stopService(intent);
            }
        });

        mSendBroadcastBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 2 способ. Через LocalBroadcastManager - но это будет долго
                sendBroadcast(new Intent(SimpleReceiver.SIMPLE_ACTION)); //1/способ. Отправляем в Broadcast всей системе

            }
        });

        mSimpleReceiver = new SimpleReceiver((TextView) findViewById(R.id.tv_time));
        mIntentFilter = new IntentFilter(SimpleReceiver.SIMPLE_ACTION);
        /*
        Соответственно, переходим в MainActivity, new Intent, и в качестве action'а передаём этот
        самый action, который мы создали. SimpleReceiver. SIMPLE_ACTION. Шикарно. И в intentFilter говорим,
        что мы теперь ловим simpleReceiver. SimpleReceiver. SIMPLE_ACTION. По кнопке передаём broadcast
        с action'ом, в ресивере, в intentFilter ловим этот action, регистрируем ресивер onResume,
        убираем регистрацию в onPause так же, как и раньше, и при ловле action'а показываем тост.
         */
    }

    @Override
    protected void onResume() { // Зарегистрировали динамисемки на изменение режима самолета // потом изменили на action
        super.onResume();
        // mIntentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(mSimpleReceiver, mIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mSimpleReceiver);
    }
}