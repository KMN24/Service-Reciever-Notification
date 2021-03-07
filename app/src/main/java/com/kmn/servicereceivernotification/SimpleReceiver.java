package com.kmn.servicereceivernotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimpleReceiver extends BroadcastReceiver {

    // SIMPLE ACTION = Кастомные события, кастомные action'ы нужно делать как можно более уникальными,
    // поэтому хорошим подходом считается использовать в качестве префикса action'а название пакета приложения.
    public static final String SIMPLE_ACTION = "com.kmn.servicereceivernotification.SIMPLE_ACTION";

    @Override
    public void onReceive(Context context, Intent intent) { // создали ресивер
        Toast.makeText(context, intent.getAction(), Toast.LENGTH_SHORT).show();

    }
}
