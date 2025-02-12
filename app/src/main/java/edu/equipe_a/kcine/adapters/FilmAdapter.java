/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.adapters;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.work.Data;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import edu.equipe_a.kcine.MainActivity;
import edu.equipe_a.kcine.R;
import edu.equipe_a.kcine.entries.Article;
import edu.equipe_a.kcine.entries.Film;
import edu.equipe_a.kcine.tools.HttpAsyncGet;
import edu.equipe_a.kcine.tools.PostExecuteActivity;
import edu.equipe_a.kcine.tools.ScheduledNotificationManager;
import edu.equipe_a.kcine.tools.SessionManager;

/**
 * Adapter pour les films
 */
public class FilmAdapter extends BaseAdapter implements PostExecuteActivity<Article> {

    private final Context context; // Contexte de l'application
    private final List<Film> films; // Liste des films
    private final int cinemaPosition; // Position du cinéma

    private final LayoutInflater inflater; // Pour instancier le layout

    /**
     * Constructeur
     *
     * @param context Contexte de l'application
     * @param films Liste des films
     * @param cinemaPosition Position du cinéma
     */
    public FilmAdapter(Context context, List<Film> films, int cinemaPosition) {
        this.context = context;
        this.films = films;
        this.cinemaPosition = cinemaPosition;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return films.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //definition des elements de la vue
        View view = inflater.inflate(R.layout.adapter_film, null);
        TextView name = view.findViewById(R.id.titreTextView);
        TextView length = view.findViewById(R.id.dureeTextView);
        TextView genre = view.findViewById(R.id.LanguageTextView);
        TextView summary = view.findViewById(R.id.synopsisTextView);
        ImageView image = view.findViewById(R.id.imageFilm);
        name.setText(films.get(position).getTitle());

        Log.d("FilmAdapter", "getView: " + films.get(position).getTitle());

        length.setText(films.get(position).getDuration() + " minutes");
        genre.setText(films.get(position).getOriginalLanguage());
        summary.setText(films.get(position).getDescription());
        Picasso.get().load(films.get(position).getImageLink()).into(image); // chargement de l'image en ligne (asynchrone)

        ConstraintLayout constraintLayout = view.findViewById(R.id.constraint); // layout parent des boutons
        HashMap<Integer, List<Integer>> schedule = films.get(position).getScheduleByRoom(cinemaPosition);
        /* Gestion de notre liste :
         * pour ne pas avoir un api qui mange trop, puisqu'il s'agit d'une appli qui n'est pas réelle,
         * au lieu de générer pour toute la france des films, on a la liste des 20 cinémas les plus proche,
         * dont peut importe la localisation, on a les horaires de projection pour chaque jour de la semaine
         * pour chaque cinéma (le plus proche de l'utilisateur aura toujours les memes films/horaires)
         * */
        int previousButtonId = summary.getId();
        if (schedule == null) {
            return view;
        }

        for (Integer day : schedule.keySet()) { // pour chaque jour sur le calendrier
            List<Integer> times = schedule.get(day); // on récupère les horaires de projection
            for (int time : times) {
                Button buttonBook = new Button(context);
                //gestion de l'heure
                Date date = new Date();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR_OF_DAY, time);
                calendar.set(Calendar.MINUTE, 0);
                calendar.add(Calendar.DATE, day);
                final Date dateF = calendar.getTime();
                buttonBook.setText(DateUtils.formatDateTime(context, dateF.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_TIME));
                //gestion de l'id
                buttonBook.setId(View.generateViewId());
                constraintLayout.addView(buttonBook);
                ConstraintSet constraintSet = new ConstraintSet(); // pour les contraintes du nouveau bouton
                constraintSet.clone(constraintLayout); // cloner les contraintes du layout
                constraintSet.connect(buttonBook.getId(), ConstraintSet.TOP, previousButtonId, ConstraintSet.BOTTOM, 16); // pour le premier bouton, on le connecte au synopsis
                constraintSet.connect(buttonBook.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
                constraintSet.connect(buttonBook.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
                constraintSet.applyTo(constraintLayout);
                previousButtonId = buttonBook.getId(); // Mettre à jour l'id du bouton précédent

                buttonBook.setOnClickListener(v -> {
                    String apiEndpoint = "http://iut.hyside.fr:9090/article/add" +
                            "?userId=" + SessionManager.getUserId() +
                            "&filmName=" + films.get(position).getTitle().replace(" ", "%20") +
                            "&filmDate=" + DateFormat.format("yyyy-MM-dd HH:mm", dateF).toString().replace(" ", "%20").replace(":", "%3A") +
                            "&filmDuration=" + films.get(position).getDuration() +
                            "&cinemaName=" + cinemaPosition +
                            "&cinemaAddress=" + "adresse".replace(" ", "%20");

                    new HttpAsyncGet<>(apiEndpoint, Article.class, this, new ProgressDialog(context.getApplicationContext()));

                    Data data = new Data.Builder()
                            .putString("title", "Votre séance commence bientôt !")
                            .putString("description", "Votre réservation pour le film " + films.get(position).getTitle() + " le " + DateFormat.format("yyyy-MM-dd HH:mm", dateF).toString() + " va commencer !.")
                            .build();

                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) - 2);

                    ScheduledNotificationManager.scheduleNotification(data, calendar, context);
                });

            }
        }
        return view;
    }

    @Override
    public void onPostExecute(List<Article> itemList) {
        ((Activity) context).runOnUiThread(() -> {
            final ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.cart_icon); // Remplacez "votre_image" par le nom de votre image dans les ressources
            imageView.setId(View.generateViewId());
            final ConstraintLayout constraintLayout = ((Activity) context).findViewById(R.id.constraint); // layout parent de l'image
            constraintLayout.addView(imageView);

            // Définir les contraintes pour centrer l'ImageView dans ConstraintLayout
            final ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.leftToLeft = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.rightToRight = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
            layoutParams.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            imageView.setLayoutParams(layoutParams);

            // set image size
            imageView.getLayoutParams().height = 500;
            imageView.getLayoutParams().width = 500;

            // set the image priority to make image top of the view
            imageView.bringToFront();

            // Créer les animations pour déplacer l'image vers le coin supérieur droit
            final ObjectAnimator translateX = ObjectAnimator.ofFloat(imageView, "translationX", 0, (float) context.getResources().getDisplayMetrics().widthPixels / 3);
            final ObjectAnimator translateY = ObjectAnimator.ofFloat(imageView, "translationY", 0, (float) -context.getResources().getDisplayMetrics().heightPixels / 3);
            final ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
            final ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 0f);

            // Définir la durée des animations
            translateX.setDuration(2000);
            translateY.setDuration(2000);
            scaleDownX.setDuration(2000);
            scaleDownY.setDuration(2000);

            // Créer un ensemble d'animations pour les exécuter simultanément
            final AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(translateX, translateY, scaleDownX, scaleDownY);

            // Démarrer l'animation
            animatorSet.start();

            animatorSet.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    constraintLayout.removeView(imageView);
                    super.onAnimationEnd(animation);
                    // Code à exécuter à la fin de l'animation
                    Toast.makeText(context, "Réservation confirmée !", Toast.LENGTH_SHORT).show();
                    context.startActivity(new Intent(context, MainActivity.class));
                    ((Activity) context).finish();
                }
            });
        });
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
    }

}
