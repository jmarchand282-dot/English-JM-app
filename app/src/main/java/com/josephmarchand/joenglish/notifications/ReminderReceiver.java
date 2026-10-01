package com.josephmarchand.joenglish.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.josephmarchand.joenglish.R;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "joenglish_reminders";
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManager manager =
                (NotificationManager) context.getSystemService(
                        Context.NOTIFICATION_SERVICE
                );

        if (manager == null) {
            return;
        }

        createChannel(manager);

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_joenglish)
                        .setContentTitle("JoEnglish 📚")
                        .setContentText(
                                "C'est l'heure de pratiquer ton anglais !"
                        )
                        .setPriority(
                                NotificationCompat.PRIORITY_DEFAULT
                        )
                        .setAutoCancel(true);

        manager.notify(NOTIFICATION_ID, notification.build());
    }

    private void createChannel(NotificationManager manager) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Rappels JoEnglish",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );

            channel.setDescription(
                    "Notifications pour rappeler les séances d'anglais."
            );

            manager.createNotificationChannel(channel);
        }
    }
}
