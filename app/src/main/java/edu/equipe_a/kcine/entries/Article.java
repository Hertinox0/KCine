/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.entries;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 *  Classe Article
 */
public class Article {

    // Identifiant de l'article
    @JsonProperty("id")
    private long id;

    // Identifiant de l'utilisateur
    @JsonProperty("userId")
    private long userId;

    // Nom du film
    @JsonProperty("filmName")
    private String filmName;

    // Date de sortie du film
    @JsonProperty("filmDate")
    private Date filmDate;

    // Durée du film
    @JsonProperty("filmDuration")
    private int filmDuration;

    /**
     * Getter de l'identifiant de l'article
     * @return long
     */
    public long getId() {
        return this.id;
    }

    /**
     * Getter de l'identifiant de l'utilisateur
     * @return long
     */
    public long getUserId() {
        return this.userId;
    }

    /**
     * Getter du nom du film
     * @return String
     */
    public String getFilmName() {
        return this.filmName;
    }

    /**
     * Getter de la date de sortie du film
     * @return Date
     */
    public Date getFilmDate() {
        return this.filmDate;
    }

    /**
     * Getter de la durée du film
     * @return int
     */
    public int getFilmDuration() {
        return this.filmDuration;
    }

}
