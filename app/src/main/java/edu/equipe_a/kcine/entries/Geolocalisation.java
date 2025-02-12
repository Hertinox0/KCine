/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.entries;

import com.fasterxml.jackson.annotation.JsonProperty;

import edu.equipe_a.kcine.parcelables.GeolocalisationParcelable;

public class Geolocalisation {

    // Longitude
    @JsonProperty("lon")
    private double lon;

    // Latitude
    @JsonProperty("lat")
    private double lat;


    /**
     * Méthode renvoyant le parcelable de la géolocalisation
     * @return le parcelable de la géolocalisation
     */
    public GeolocalisationParcelable toParcelable(){
        return new GeolocalisationParcelable(lon, lat);
    }

}
