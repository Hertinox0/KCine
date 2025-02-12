/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.tools;

import java.util.List;

/**
 * Interface pour les activités qui doivent exécuter du code après une tâche asynchrone
 * @param <T> Type de l'objet à traiter
 */
public interface PostExecuteActivity<T> {

    /**
     * Méthode appelée après l'exécution de la tâche asynchrone
     * @param itemList Liste des objets à traiter
     */
    void onPostExecute(List<T> itemList);

    /**
     * Méthode pour exécuter du code sur le thread principal
     * @param runnable Code à exécuter
     */
    void runOnUiThread(Runnable runnable);

}
