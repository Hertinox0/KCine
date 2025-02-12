/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.entries;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.List;

public class Film {

    // Liste des salles
    @JsonProperty("rooms")
    private List<Integer> rooms;

    // Liste des jours
    @JsonProperty("days")
    private List<Integer> days;

    // HashMap pour chaque salle, une liste de jours et d'horaires de chaque jour
    @JsonProperty("scheduleByRoom")
    private HashMap<Integer, HashMap<Integer, List<Integer>>> scheduleByRoom;

    // Informations du film
    @JsonProperty("title")
    private String title;

    // Durée du film en minutes
    @JsonProperty("duration")
    private int duration;

    // Lien de l'image du film
    @JsonProperty("imageLink")
    private String imageLink;

    // Description du film
    @JsonProperty("description")
    private String description;

    // Date de sortie du film
    @JsonProperty("releaseDate")
    private String releaseDate;

    // Langue originale du film
    @JsonProperty("originalLanguage")
    private String originalLanguage;

    public String getTitle() {
        return title;
    }

    public int getDuration() {
        return duration;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public HashMap<Integer, List<Integer>> getScheduleByRoom(int room) {
        return scheduleByRoom.get(room);
    }

    public HashMap<Integer, HashMap<Integer, List<Integer>>> getScheduleByRoom() {
        return scheduleByRoom;
    }

}
