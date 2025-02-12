/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.tools;

import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public abstract class ScheduledNotificationManager {

    private static final ArrayList<Long> scheduledNotifications = new ArrayList<>();

    /**
     * Programmer une notification à unedate et heure donnée
     *
     * @param data     Les données à envoyer à la notification
     *                 (doivent contenir les clés "title" et "description")
     *                 pour le titre et la description de la notification
     * @param calendar La date et l'heure à laquelle programmer la notification
     * @param context  Le contexte de l'application pour programmer la notification
     */
    public static void scheduleNotification(Data data, Calendar calendar, Context context) {
        long currentTimeMillis = System.currentTimeMillis();
        calendar.set(Calendar.SECOND, 0);
        long notificationTimeMillis = calendar.getTimeInMillis();
        long delayInMillis = notificationTimeMillis - currentTimeMillis;

        // Conversion en secondes
        long delayInSeconds = delayInMillis / 1000;

        scheduledNotifications.add(notificationTimeMillis);

        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInitialDelay(delayInSeconds, TimeUnit.SECONDS)
                .setInputData(data)
                .build();

        WorkManager.getInstance(context).enqueue(notificationWork);
        Log.d("ScheduledNotificationManager", "Notification programmée pour le " + calendar.getTime());
    }

    public static void removeScheduledNotification(Calendar calendar) {
        long notificationTimeMillis = calendar.getTimeInMillis();

        if (scheduledNotifications.remove(notificationTimeMillis)) {
            Log.d("ScheduledNotificationManager", "Notification supprimée pour le " + calendar.getTime());
        } else {
            Log.d("ScheduledNotificationManager", "Aucune notification programmée pour le " + calendar.getTime());
        }

    }
}
