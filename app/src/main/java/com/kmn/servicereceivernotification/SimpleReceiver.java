package com.kmn.servicereceivernotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class SimpleReceiver extends BroadcastReceiver {

    private WeakReference<TextView> mTextViewWeakReference;

    public SimpleReceiver(TextView textView){
        mTextViewWeakReference = new WeakReference<>(textView);
    }

    // SIMPLE ACTION = Кастомные события, кастомные action'ы нужно делать как можно более уникальными,
    // поэтому хорошим подходом считается использовать в качестве префикса action'а название пакета приложения.
    public static final String SIMPLE_ACTION = "com.kmn.s ervicereceivernotification.SIMPLE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) { // создали ресивер
        long time = intent.getLongExtra(CountService.TIME, 0L);
        Toast.makeText(context, "current time is " + time, Toast.LENGTH_SHORT).show();

//        Intent launchActivityIntent = new Intent(context, TempActivity.class);
//        launchActivityIntent.putExtra(CountService.TIME, time);
//        context.startActivity(launchActivityIntent);

        TextView textView = mTextViewWeakReference.get();
        StringBuilder builder = new StringBuilder(textView.getText().toString());
        builder.append(time).append("\n");
        textView.setText(builder.toString());

        /*
          Мы видим то, что мы запускаем сервис, в сервисе мы получаем новые данные, данные оборачиваем в Intent,
          отправляем в BroadcastReceiver, BroadcastReceiver достает эти данные и записывает в TextView,
          которую он получил при создании, в конструкторе, и которой он хранит в слабой ссылке, это дичь,
          это ужас, так делать нельзя вообще ни разу. Для взаимодействия с интерфейсом Service, пусть ч
          ерез BroadcastReceiver, но они не очень хорошо подходят, они вообще не подходят, то есть это можно
          сделать я это сделал только что, но это неправильно. Если вам нужно, чтобы ваш сервис взаимодействовал
           с Activity, чтобы он получал и отправлял. Получал результаты и отправлял запросы крайнее так, то вам
           нужно не просто запустить сервис вам нужно его привязать, то есть вместо StartSevice называть
            [inaudible] BindService. И это очень интересная тема настолько интересная, что я оставляю вам
            на самостоятельное изучение
         */
    }
}
