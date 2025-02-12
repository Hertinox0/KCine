/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import edu.equipe_a.kcine.R;

/**
 * Classe permettant de gérer les notifications
 */
public class NotificationWorker extends Worker {

    /**
     * Constructeur de la classe
     *
     * @param context      Le contexte de l'application
     * @param workerParams Les paramètres du Worker
     */
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Méthode appelée lors de l'exécution du Worker
     *
     * @return Result.success() si tout s'est bien passé
     */
    @NonNull
    @Override
    public Result doWork() {
        final String title = getInputData().getString("title");
        final String description = getInputData().getString("description");

        createNotification(title, description);
        return Result.success();
    }

    /**
     * Crée une notification avec le titre et la description donnés
     *
     * @param title       Le titre de la notification
     * @param description La description de la notification
     */
    private void createNotification(String title, String description) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                .setSmallIcon(R.drawable.logo_kcine)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_HIGH) // Priorité élevée pour les notifications flottantes
                .setStyle(new NotificationCompat.BigTextStyle().bigText(description)); // Style pour afficher le texte complet

        final NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(
                this.getApplicationContext(),
                Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return;
        notificationManager.notify(123, builder.build());
    }

}
