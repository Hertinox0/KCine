/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
 * Activité pour l'inscription d'un utilisateur
 */
public class RegisterActivity extends AppCompatActivity implements PostExecuteActivity<User> {

    /**
     * Méthode appelée à la création de l'activité
     * @param savedInstanceState état de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        final TextView login = findViewById(R.id.login); // Bouton pour aller à l'activité de connexion
        final Button register = findViewById(R.id.register); // Bouton pour s'inscrire
        final TextView firstName = findViewById(R.id.firstName); // Champ pour le prénom
        final TextView lastName = findViewById(R.id.lastName); // Champ pour le nom
        final TextView email = findViewById(R.id.email); // Champ pour l'email
        final TextView emailConfirmation = findViewById(R.id.emailConfirmation); // Champ pour la confirmation de l'email
        final TextView password = findViewById(R.id.password); // Champ pour le mot de passe
        final TextView passwordConfirmation = findViewById(R.id.passwordConfirmation); // Champ pour la confirmation du mot de passe

        /* Listener pour le bouton d'inscription */
        register.setOnClickListener(v -> {
            // Vérification des champs
            if (firstName.getText().toString().isEmpty() || lastName.getText().toString().isEmpty() || email.getText().toString().isEmpty() || emailConfirmation.getText().toString().isEmpty() || password.getText().toString().isEmpty() || passwordConfirmation.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification de l'email
            if (!email.getText().toString().equals(emailConfirmation.getText().toString())) {
                Toast.makeText(this, "Emails do not match", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
                Toast.makeText(this, "Invalid email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Vérification du mot de passe
            if (!password.getText().toString().equals(passwordConfirmation.getText().toString())) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Appel de la méthode d'inscription
            this.register(firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());
        });


        /* Listener pour le bouton de connexion */
        login.setOnClickListener(v -> {
            finishAffinity(); // Ferme toutes les activités empilées
            startActivity(new Intent(this, LoginActivity.class)); // Lance l'activité de connexion
        });

    }

    /**
     * Méthode pour l'inscription d'un utilisateur
     * @param firstName prénom
     * @param lastName nom
     * @param email email
     * @param password mot de passe
     */
    public void register(String firstName, String lastName, String email, String password) {
        String url = "http://localhost:9090/user/create?firstName=" + firstName + "&lastName=" + lastName + "&email=" + email + "&password=" + password;
        new HttpAsyncGet<>(url, User.class, this, null);
    }

    /**
     * Méthode appelée après l'exécution de la requête HTTP
     * @param itemList liste des objets retournés par la requête
     */
    @Override
    public void onPostExecute(List<User> itemList) {
        if(itemList == null || itemList.isEmpty()) {
            TextView error = findViewById(R.id.error);
            error.setText(getString(R.string.email_already_exist));
            error.setTextColor(getColor(R.color.red));

            // Animation de l'erreur avec un effet de zoom
            error.animate().scaleX(1.2f).scaleY(1.2f).setDuration(250).withEndAction(() -> {
                error.animate().scaleX(1f).scaleY(1f).setDuration(250).start();
            }).start();
            return;
        }

        // Connexion réussie
        final User user = itemList.get(0);
        Log.d("RegisterActivity", "onPostExecute: " + user.getFirstName() + " " + user.getLastName());

        final TextView success = findViewById(R.id.error);
        success.setText(getString(R.string.register_success, user.getFirstName()));
        success.setTextColor(getColor(R.color.green));

        // Animation de succès avec un effet de zoom
        success.animate().scaleX(1.2f).scaleY(1.2f).setDuration(250).withEndAction(() -> {
            success.animate().scaleX(1f).scaleY(1f).setDuration(250).start();
        }).start();

        // Sauvegarde de l'utilisateur
        SessionManager.init(this);
        SessionManager.saveUserId(user.getId(), false);
        Log.d("RegisterActivity", "onPostExecute User Saved: " + SessionManager.getUserId());

        // Redirection vers l'activité principale
        success.postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1000);

    }

}
