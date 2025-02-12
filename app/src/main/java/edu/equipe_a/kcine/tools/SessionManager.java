/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Classe permettant de gérer la session de l'utilisateur
 */
public abstract class SessionManager {

    private static final String PREF_NAME = "LoginSession"; // Nom du fichier de préférences
    private static final String KEY_USER_ID = "userId"; // Clé pour l'identifiant de l'utilisateur
    private static final String KEY_KEEP_CONNECTED = "keepConnected"; // Clé pour le maintien de la connexion
    private static SharedPreferences sharedPreferences; // Objet de préférences partagées
    private static SharedPreferences.Editor editor; // Éditeur des préférences partagées

    /**
     * Initialise les préférences partagées
     *
     * @param context Contexte de l'application
     */
    public static void init(Context context) {
        if (sharedPreferences == null) { // Si les préférences partagées ne sont pas initialisées
            sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE); // Initialisation des préférences partagées
            editor = sharedPreferences.edit(); // Initialisation de l'éditeur des préférences partagées
        }
    }

    /**
     * Sauvegarde l'identifiant de l'utilisateur et le maintien de la connexion
     *
     * @param userId         Identifiant de l'utilisateur
     * @param keepConnected  Maintien de la connexion
     */
    public static void saveUserId(String userId, boolean keepConnected) {
        if (editor != null) { // Si l'éditeur des préférences partagées est initialisé
            editor.putString(KEY_USER_ID, userId); // Sauvegarde de l'identifiant de l'utilisateur
            editor.putString(KEY_KEEP_CONNECTED, String.valueOf(keepConnected)); // Sauvegarde du maintien de la connexion
            editor.apply(); // Application des modifications
        }
    }

    /**
     * Retourne l'identifiant de l'utilisateur
     *
     * @return Identifiant de l'utilisateur
     */
    public static String getUserId() {
        if (sharedPreferences != null) // Si les préférences partagées sont initialisées
            return sharedPreferences.getString(KEY_USER_ID, null); // Retourne l'identifiant de l'utilisateur
        return null; // Retourne null si les préférences partagées ne sont pas initialisées
    }

    /**
     * Retourne le maintien de la connexion
     *
     * @return Maintien de la connexion
     */
    public static boolean keepConnected() {
        if (sharedPreferences != null) // Si les préférences partagées sont initialisées
            return Boolean.parseBoolean(sharedPreferences.getString(KEY_KEEP_CONNECTED, String.valueOf(false))); // Retourne le maintien de la connexion
        return false; // Retourne false si les préférences partagées ne sont pas initialisées
    }

    /**
     * Efface l'identifiant de l'utilisateur et le maintien de la connexion
     */
    public static void clearUserId() {
        if (editor != null) { // Si l'éditeur des préférences partagées est initialisé
            editor.remove(KEY_USER_ID); // Efface l'identifiant de l'utilisateur
            editor.remove(KEY_KEEP_CONNECTED); // Efface le maintien de la connexion
            editor.apply(); // Application des modifications
        }
    }

}
