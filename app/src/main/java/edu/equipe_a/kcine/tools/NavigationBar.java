/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import edu.equipe_a.kcine.AccountActivity;
import edu.equipe_a.kcine.MainActivity;
import edu.equipe_a.kcine.NotificationsActivity;
import nl.joery.animatedbottombar.AnimatedBottomBar;

/**
 * Classe permettant de gérer la navigation entre les différentes activités de l'application.
 */
public class NavigationBar {

    private final AnimatedBottomBar bar; // Barre de navigation
    private final Context context; // Contexte de l'application
    private final Activity activity; // Activité courante

    /**
     * Constructeur de la classe.
     *
     * @param bar Barre de navigation
     * @param context Contexte de l'application
     * @param activity Activité courante
     * @param index Index de l'onglet à sélectionner
     */
    public NavigationBar(AnimatedBottomBar bar, Context context, Activity activity, int index) {
        this.bar = bar;
        this.context = context;
        this.activity = activity;
        this.bar.selectTabAt(index, false); // Sélection de l'onglet courant
    }

    /**
     * Méthode permettant d'ajouter les écouteurs sur les onglets de la barre de navigation.
     */
    public void registerListeners() {

        this.bar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {

            @Override
            public void onTabSelected(int lastIndex, AnimatedBottomBar.Tab lastTab, int newIndex, @NonNull AnimatedBottomBar.Tab newTab) {
                if (lastIndex == newIndex) return; // Si l'onglet sélectionné est déjà actif, on ne fait rien

                switch (newIndex) { // Redirection vers l'activité correspondante

                    case 0 -> { // Accueil
                        final Intent intent = new Intent(NavigationBar.this.context, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        NavigationBar.this.context.startActivity(intent);
                        NavigationBar.this.activity.finish();
                    }

                    case 1 -> { // Compte
                        final Intent intent = new Intent(NavigationBar.this.context, AccountActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        NavigationBar.this.context.startActivity(intent);
                        NavigationBar.this.activity.finish();
                    }

                    case 2 -> { // Notifications
                        final Intent intent = new Intent(NavigationBar.this.context, NotificationsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        NavigationBar.this.context.startActivity(intent);
                        NavigationBar.this.activity.finish();
                    }

                }
            }

            @Override
            public void onTabReselected(int index, @NonNull AnimatedBottomBar.Tab tab) {
            }

        });

    }

}
