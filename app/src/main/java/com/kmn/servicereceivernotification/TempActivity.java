package com.kmn.servicereceivernotification;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        long longExtra =  getIntent().getLongExtra(CountService.TIME, 0);

        TextView tvTime =  findViewById(R.id.tv_time);
        tvTime.setText( "Time is " +  longExtra);
        /*
        Text ViewTime setTextLongExtra, Timeis. Проверим еще раз, что мы сделали.
        В CountService мы передаем в Intent, который мы передаем Broadcast, которым передаем Broadcastо Receiver,
        мы передаем текущее время в Receiver мы это время ловим, показываем в Toast и запускаем. Задаем новый Intent,
        загоняем туда данные, это самое время, и запускаем новый Aсtivity. В новом Activity, мы показываем полученное
        время в TextView. Щелкаем на play и запускаем сервис и через бац запустилось новое Activity и все, потому что мы
        переключились на новые Activity. Receiver больше не работает, потому что он остался в старом Activity.
         */

    }
}