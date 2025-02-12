/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine;

import android.app.ProgressDialog;
import android.content.Intent;
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
 * Activité de l'écran de compte utilisateur
 */
public class AccountActivity extends AppCompatActivity implements PostExecuteActivity<Article> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialisation de la barre de navigation
        final AnimatedBottomBar bar = findViewById(R.id.top_bar);
        new NavigationBar(bar, this.getApplicationContext(), this, 1).registerListeners();

        findViewById(R.id.logout).setOnClickListener(v -> disconnect()); // Gestion du bouton de déconnexion

        initArticlesList(); // Initialisation de la liste des articles
    }

    /**
     * Déconnexion de l'utilisateur
     */
    private void disconnect() {
        SessionManager.init(this);
        SessionManager.clearUserId();
        SessionManager.clearUserId();
        finishAffinity(); // Ferme toutes les activités empilées
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * Initialisation de la liste des articles
     */
    private void initArticlesList() {
        final String url = "http://iut.hyside.fr:9090/article/all?userId=" + SessionManager.getUserId();
        Log.d("AccountActivity", "initArticlesList: " + url);
        new HttpAsyncGet<>(url, Article.class, this, new ProgressDialog(AccountActivity.this) );
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0); // Suppression de l'animation de transition entre les activités
    }

    /**
     * Méthode appelée après la récupération des articles
     * @param itemList Liste des articles récupérés
     */
    @Override
    public void onPostExecute(List<Article> itemList) {
        final ListView listView = findViewById(R.id.articleList);
        final ArrayList<Article> articleList = new ArrayList<>();
        if(itemList == null) // Si aucun article n'a été trouvé
            findViewById(R.id.no_reservation).setVisibility(View.VISIBLE); // Affichage d'un message
        else // Sinon
            articleList.addAll(itemList); // Ajout des articles à la liste
        ListAdapter articleAdapter = new ArticleAdapter(this, articleList);
        listView.setAdapter(articleAdapter);
    }

}
