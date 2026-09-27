package com.josephmarchand.joenglish.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.josephmarchand.joenglish.MainActivity;
import com.josephmarchand.joenglish.R;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID =
            "joenglish_reminders";

    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onReceive(
            Context context,
            Intent intent
    ) {

        createNotificationChannel(context);

        Intent openIntent =
                new Intent(context, MainActivity.class);

        openIntent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        );

        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        openIntent,
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                                ? PendingIntent.FLAG_UPDATE_CURRENT
                                | PendingIntent.FLAG_IMMUTABLE
                                : PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationCompat.Builder notification =
                new NotificationCompat.Builder(
                        context,
                        CHANNEL_ID
                )
                .setSmallIcon(R.drawable.ic_joenglish)
                .setContentTitle("JoEnglish")
                .setContentText(
                        "C'est le moment d'apprendre quelques mots d'anglais !"
                )
                .setPriority(
                        NotificationCompat.PRIORITY_DEFAULT
                )
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        try {

            NotificationManagerCompat
                    .from(context)
                    .notify(
                            NOTIFICATION_ID,
                            notification.build()
                    );

        } catch (SecurityException ignored) {
            // Notification refusée par l'utilisateur.
        }
    }

    private void createNotificationChannel(
            Context context
    ) {

        if (Build.VERSION.SDK_INT >=
                Build.VERSION_CODES.O) {

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            "Rappels JoEnglish",
                            NotificationManager.IMPORTANCE_DEFAULT
                    );

            channel.setDescription(
                    "Rappels pour continuer ton apprentissage."
            );

            NotificationManager manager =
                    context.getSystemService(
                            NotificationManager.class
                    );

            if (manager != null) {
                manager.createNotificationChannel(
                        channel
                );
            }
        }
    }
}
