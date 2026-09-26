package com.josephmarchand.joenglish.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.josephmarchand.joenglish.MainActivity;
import com.josephmarchand.joenglish.R;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "joenglish_reminders";
    private static final int NOTIFICATION_ID = 1001;

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("JoEnglish")
                        .setContentText("C'est le moment d'apprendre quelques mots d'anglais !")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

        Intent openAppIntent = new Intent(context, MainActivity.class);
        openAppIntent.setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TOP
        );

        android.app.PendingIntent pendingIntent =
                android.app.PendingIntent.getActivity(
                        context,
                        0,
                        openAppIntent,
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                                ? android.app.PendingIntent.FLAG_UPDATE_CURRENT
                                | android.app.PendingIntent.FLAG_IMMUTABLE
                                : android.app.PendingIntent.FLAG_UPDATE_CURRENT
                );

        builder.setContentIntent(pendingIntent);

        try {
            NotificationManagerCompat.from(context)
                    .notify(NOTIFICATION_ID, builder.build());
        } catch (SecurityException ignored) {
            // Les notifications peuvent être refusées par l'utilisateur.
        }
    }

    private void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "Rappels JoEnglish";
            String description =
                    "Notifications de rappel pour apprendre l'anglais";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel =
                    new NotificationChannel(
                            CHANNEL_ID,
                            name,
                            importance
                    );

            channel.setDescription(description);

            NotificationManager notificationManager =
                    context.getSystemService(NotificationManager.class);

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
          }
