package edu.washington.ttulga.awty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
/**
 * Created by mbmtuvshin on 2/22/15.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String message = intent.getStringExtra("message");
        String phone = intent.getStringExtra("phone");

        // For our recurring task, we'll just display a message
        Toast.makeText(context, phone + ": " + message, Toast.LENGTH_SHORT).show();
    }
}