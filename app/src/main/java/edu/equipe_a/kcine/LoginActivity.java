/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import edu.equipe_a.kcine.entries.User;
import edu.equipe_a.kcine.tools.HttpAsyncGet;
import edu.equipe_a.kcine.tools.PostExecuteActivity;
import edu.equipe_a.kcine.tools.SessionManager;

/**
 * Activité de connexion
 */
public class LoginActivity extends AppCompatActivity implements PostExecuteActivity<User> {

    private Button loginButton;

    /**
     * Méthode appelée à la création de l'activité
     * @param savedInstanceState état de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Verifie si l'utilisateur a demandé à rester connecté
        SessionManager.init(this);
        if (SessionManager.keepConnected()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        loginButton = findViewById(R.id.login);
        final TextView register = findViewById(R.id.register);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);

        // Permet de valider le formulaire en appuyant sur la touche "Done" du clavier
        password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == 6) {
                loginButton.performClick();
                return true;
            }
            return false;
        });

        // Vérifie les champs et lance la requête de connexion
        loginButton.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {
                Toast.makeText(this, "Merci de remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            this.login(email.getText().toString(), password.getText().toString());
        });

        // Redirige vers l'activité d'inscription
        register.setOnClickListener(v -> {
            finishAffinity(); // Ferme toutes les activités empilées
            startActivity(new Intent(this, RegisterActivity.class));
        });

    }

    /**
     * Méthode de connexion
     * @param email email de l'utilisateur
     * @param password mot de passe de l'utilisateur
     */
    public void login(String email, String password) {
        String url = "http://iut.hyside.fr:9090/user/verify?email=" + email + "&password=" + password;
        new HttpAsyncGet<>(url, User.class, this, new ProgressDialog(LoginActivity.this));
        loginButton.setEnabled(false);
    }

    /**
     * Méthode appelée après la requête de connexion
     * @param itemList liste des utilisateurs
     */
    @Override
    public void onPostExecute(List<User> itemList) {
        if(itemList == null || itemList.isEmpty()) {
            // Gestion de l'erreur de connexion (pas d'utilisateur trouvé)
            Log.d("LoginActivity", "onPostExecute: No user found");
            TextView error = findViewById(R.id.error);
            error.setText(getString(R.string.invalid_credentials));
            error.setTextColor(getColor(R.color.red));

            // Animation d'erreur avec un effet de zoom
            error.animate().scaleX(1.2f).scaleY(1.2f).setDuration(250).withEndAction(() -> {
                error.animate().scaleX(1f).scaleY(1f).setDuration(250).start();
            }).start();

            loginButton.setEnabled(true);
            return;
        }

        // Connexion réussie
        final User user = itemList.get(0);
        Log.d("LoginActivity", "onPostExecute: " + user.getFirstName() + " " + user.getLastName());

        final TextView success = findViewById(R.id.error);
        success.setText(getString(R.string.login_success, user.getFirstName() + " " + user.getLastName()));
        success.setTextColor(getColor(R.color.green));

        final CheckBox keepLoggedIn = findViewById(R.id.keepLogged);
        SessionManager.init(this);
        SessionManager.saveUserId(user.getId(), keepLoggedIn.isChecked());
        Log.d("LoginActivity", "onPostExecute User Saved: " + SessionManager.getUserId());

        // Animation de succès avec un effet de zoom
        success.animate().scaleX(1.2f).scaleY(1.2f).setDuration(250).withEndAction(() -> {
            success.animate().scaleX(1f).scaleY(1f).setDuration(250).start();
        }).start();

        // Redirection vers l'activité principale
        success.postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1000);

    }

}
