/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe GeolocalisationParcelable
 */
public class GeolocalisationParcelable implements Parcelable {

    // Longitude
    @JsonProperty("lon")
    private double lon;

    // Latitude
    @JsonProperty("lat")
    private double lat;

    /**
     * Constructeur
     *
     * @param lon Longitude
     * @param lat Latitude
     */
    public GeolocalisationParcelable(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * Constructeur
     *
     * @param in Parcel
     */
    protected GeolocalisationParcelable(Parcel in) {
        lon = in.readDouble();
        lat = in.readDouble();
    }

    public static final Creator<GeolocalisationParcelable> CREATOR = new Creator<>() {
        @Override
        public GeolocalisationParcelable createFromParcel(Parcel in) {
            return new GeolocalisationParcelable(in);
        }

        @Override
        public GeolocalisationParcelable[] newArray(int size) {
            return new GeolocalisationParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(this.lon);
        dest.writeDouble(this.lat);
    }

    public double getLon() {
        return lon;
    }

    public double getLat() {
        return lat;
    }

}
