package com.example.ande_assignment_budget.BroadcastReceiver;

import static android.content.Context.ALARM_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.example.ande_assignment_budget.R;

import java.util.Calendar;

public class BillReminder extends BroadcastReceiver{
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Perform the action for the reminder, such as showing a notification
        int reminderId = intent.getIntExtra("REMINDER_ID", -1);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                .setSmallIcon(R.drawable.ic_baseline_alarm_24)
                .setContentTitle("Bill Reminder")
                .setContentText("Time to pay your bill!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        NotificationManagerCompat.from(context).notify(reminderId, builder.build());
    }
}
