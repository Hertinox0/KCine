/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import edu.equipe_a.kcine.adapters.ArticleAdapter;
import edu.equipe_a.kcine.entries.Article;
import edu.equipe_a.kcine.tools.HttpAsyncGet;
import edu.equipe_a.kcine.tools.NavigationBar;
import edu.equipe_a.kcine.tools.PostExecuteActivity;
import edu.equipe_a.kcine.tools.SessionManager;
import nl.joery.animatedbottombar.AnimatedBottomBar;

/**
 * Activité de gestion des notifications
 */
public class NotificationsActivity extends AppCompatActivity implements PostExecuteActivity<Article> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notifications);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation de la barre de navigation
        final AnimatedBottomBar bar = findViewById(R.id.top_bar);
        new NavigationBar(bar, this.getApplicationContext(), this, 2).registerListeners();

        // Initialisation de la liste des articles
        initArticlesList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0); // Suppression de l'animation de transition entre les activités
    }

    /**
     * Initialisation de la liste des articles
     */
    private void initArticlesList() {
        final String url = "http://127.0.0.1:9090/article/notif?userId=" + SessionManager.getUserId();
        Log.d("NotificationsActivity", "initArticlesList: " + url);
        new HttpAsyncGet<>(url, Article.class, this, new ProgressDialog(NotificationsActivity.this) );
    }

    /**
     * Méthode appelée après la récupération des articles
     * @param itemList Liste des articles récupérés
     */
    @Override
    public void onPostExecute(List<Article> itemList) {
        final ListView listView = findViewById(R.id.articleList2);
        final ArrayList<Article> articleList = new ArrayList<>();
        if(itemList == null) // Si la liste est vide
            findViewById(R.id.no_notif).setVisibility(View.VISIBLE); // Affichage d'un message
        else // Sinon
            articleList.addAll(itemList); // Ajout des articles à la liste
        final ListAdapter articleAdapter = new ArticleAdapter(this, articleList);
        listView.setAdapter(articleAdapter);
    }

}
