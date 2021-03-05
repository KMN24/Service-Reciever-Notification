package com.kmn.servicereceivernotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mStartServiceBtn;
    private Button mStopServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      mStartServiceBtn = findViewById(R.id.start_service);
      mStopServiceBtn = findViewById(R.id.stop_service);

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

    }
}