/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.equipe_a.kcine.AccountActivity;
import edu.equipe_a.kcine.R;
import edu.equipe_a.kcine.entries.Article;
import edu.equipe_a.kcine.tools.HttpAsyncGet;
import edu.equipe_a.kcine.tools.PostExecuteActivity;
import edu.equipe_a.kcine.tools.ScheduledNotificationManager;

/**
 * Adapter pour les articles
 */
public class ArticleAdapter extends BaseAdapter implements PostExecuteActivity<Article> {

    private final Context context; // Contexte de l'application
    private final List<Article> articles; // Liste des articles

    private final LayoutInflater inflater;

    /**
     * Constructeur
     *
     * @param context Contexte de l'application
     * @param articles Liste des articles
     */
    public ArticleAdapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.articles.size();
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
        final View view = inflater.inflate(R.layout.adapter_article, null);
        final TextView filmName = view.findViewById(R.id.film_name);
        final TextView filmDate = view.findViewById(R.id.film_date);
        final TextView filmDuration = view.findViewById(R.id.film_duration);

        final Date filmScheduleDate = articles.get(position).getFilmDate();
        // Convert date to Calendar
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(filmScheduleDate);

        filmName.setText(articles.get(position).getFilmName());
        filmDate.setText(DateUtils.formatDateTime(context, filmScheduleDate.getTime(), DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_TIME));
        filmDuration.setText(articles.get(position).getFilmDuration() + " minutes");

        final Button button = view.findViewById(R.id.delete_button);
        button.setOnClickListener(v -> {
            final String apiEndpoint = "http://iut.hyside.fr:9090/article/remove?id=" + articles.get(position).getId();
            new HttpAsyncGet<>(apiEndpoint, Article.class, this, new ProgressDialog(context.getApplicationContext()));
            ScheduledNotificationManager.removeScheduledNotification(calendar);
        });

        return view;
    }

    @Override
    public void onPostExecute(List<Article> itemList) {
        ((Activity) context).runOnUiThread(() -> {
            Toast.makeText(context, "Réservation annulée !", Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(context, AccountActivity.class));
            ((Activity) context).finish();
        });
    }

    @Override
    public void runOnUiThread(Runnable runnable) {
    }

}
