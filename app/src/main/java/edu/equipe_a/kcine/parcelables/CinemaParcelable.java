/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.parcelables;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe permettant de stocker les informations d'un cinéma
 */
public class CinemaParcelable implements Parcelable {
    
    private int regionCnc;
    private String ndegAuto;
    private String name;
    private String region;
    private String address;
    private String inseeCode;
    private String district;
    private double districtPopulation;
    private String dep;
    private String ndegUu;
    private String urbanUnity;
    private double urbanUnityPopulation;
    private String geographicalSituation;
    private Double screens;
    private Double seats;
    private Double weekActivity;
    private Double sessions;
    private Double entries2022;
    private Double entries2021;
    private Double entriesUpgrade;
    private String entryRange;
    private String programmer;
    private String ae;
    private String categorieArtEtEssai;
    private String labelArtEtEssai;
    private String genre;
    private String multiplexe;
    private String zoneDeLaCommune;
    private Double nombreDeFilmsProgrammes;
    private Double nombreDeFilmsInedits;
    private Double nombreDeFilmsEnSemaine1;
    private Double pdmEnEntreesDesFilmsFrancais;
    private Double pdmEnEntreesDesFilmsAmericains;
    private Double pdmEnEntreesDesFilmsEuropeens;
    private Double pdm_en_entrees_des_autres_films;
    private Double filmsArtEtEssai;
    private Double pdmEnEntreesDesFilmsArtEtEssai;
    private String latitude;
    private String longitude;
    private GeolocalisationParcelable geolocation;
    private double distance;

    public CinemaParcelable() {} // Constructeur par défaut

    public CinemaParcelable(String name, String address, String inseeCode, String district, String dep, String urbanUnity, String geographicalSituation, Double screens, Double seats, Double weekActivity, Double sessions, Double entries2022, Double entries2021, Double entriesUpgrade, String entryRange, String programmer, String ae, String categorieArtEtEssai, String labelArtEtEssai, String genre, String multiplexe, String zoneDeLaCommune, Double nombreDeFilmsProgrammes, Double nombreDeFilmsInedits, Double nombreDeFilmsEnSemaine1, Double pdmEnEntreesDesFilmsFrancais, Double pdmEnEntreesDesFilmsAmericains, Double pdmEnEntreesDesFilmsEuropeens, Double pdmEnEntreesDesAutresFilms, Double filmsArtEtEssai, Double pdmEnEntreesDesFilmsArtEtEssai, GeolocalisationParcelable parcelable) {
        this.name = name;
        this.address = address;
        this.inseeCode = inseeCode;
        this.district = district;
        this.dep = dep;
        this.urbanUnity = urbanUnity;
        this.geographicalSituation = geographicalSituation;
        this.screens = screens;
        this.seats = seats;
        this.weekActivity = weekActivity;
        this.sessions = sessions;
        this.entries2022 = entries2022;
        this.entries2021 = entries2021;
        this.entriesUpgrade = entriesUpgrade;
        this.entryRange = entryRange;
        this.programmer = programmer;
        this.ae = ae;
        this.categorieArtEtEssai = categorieArtEtEssai;
        this.labelArtEtEssai = labelArtEtEssai;
        this.genre = genre;
        this.multiplexe = multiplexe;
        this.zoneDeLaCommune = zoneDeLaCommune;
        this.nombreDeFilmsProgrammes = nombreDeFilmsProgrammes;
        this.nombreDeFilmsInedits = nombreDeFilmsInedits;
        this.nombreDeFilmsEnSemaine1 = nombreDeFilmsEnSemaine1;
        this.pdmEnEntreesDesFilmsFrancais = pdmEnEntreesDesFilmsFrancais;
        this.pdmEnEntreesDesFilmsAmericains = pdmEnEntreesDesFilmsAmericains;
        this.pdmEnEntreesDesFilmsEuropeens = pdmEnEntreesDesFilmsEuropeens;
        this.filmsArtEtEssai = filmsArtEtEssai;
        this.pdmEnEntreesDesFilmsArtEtEssai = pdmEnEntreesDesFilmsArtEtEssai;
    }

    protected CinemaParcelable(Parcel in) {
        regionCnc = in.readInt();
        ndegAuto = in.readString();
        name = in.readString();
        region = in.readString();
        address = in.readString();
        inseeCode = in.readString();
        district = in.readString();
        districtPopulation = in.readDouble();
        dep = in.readString();
        ndegUu = in.readString();
        urbanUnity = in.readString();
        urbanUnityPopulation = in.readDouble();
        geographicalSituation = in.readString();
        if (in.readByte() == 0) {
            screens = null;
        } else {
            screens = in.readDouble();
        }
        if (in.readByte() == 0) {
            seats = null;
        } else {
            seats = in.readDouble();
        }
        if (in.readByte() == 0) {
            weekActivity = null;
        } else {
            weekActivity = in.readDouble();
        }
        if (in.readByte() == 0) {
            sessions = null;
        } else {
            sessions = in.readDouble();
        }
        if (in.readByte() == 0) {
            entries2022 = null;
        } else {
            entries2022 = in.readDouble();
        }
        if (in.readByte() == 0) {
            entries2021 = null;
        } else {
            entries2021 = in.readDouble();
        }
        if (in.readByte() == 0) {
            entriesUpgrade = null;
        } else {
            entriesUpgrade = in.readDouble();
        }
        entryRange = in.readString();
        programmer = in.readString();
        ae = in.readString();
        categorieArtEtEssai = in.readString();
        labelArtEtEssai = in.readString();
        genre = in.readString();
        multiplexe = in.readString();
        zoneDeLaCommune = in.readString();
        if (in.readByte() == 0) {
            nombreDeFilmsProgrammes = null;
        } else {
            nombreDeFilmsProgrammes = in.readDouble();
        }
        if (in.readByte() == 0) {
            nombreDeFilmsInedits = null;
        } else {
            nombreDeFilmsInedits = in.readDouble();
        }
        if (in.readByte() == 0) {
            nombreDeFilmsEnSemaine1 = null;
        } else {
            nombreDeFilmsEnSemaine1 = in.readDouble();
        }
        if (in.readByte() == 0) {
            pdmEnEntreesDesFilmsFrancais = null;
        } else {
            pdmEnEntreesDesFilmsFrancais = in.readDouble();
        }
        if (in.readByte() == 0) {
            pdmEnEntreesDesFilmsAmericains = null;
        } else {
            pdmEnEntreesDesFilmsAmericains = in.readDouble();
        }
        if (in.readByte() == 0) {
            pdmEnEntreesDesFilmsEuropeens = null;
        } else {
            pdmEnEntreesDesFilmsEuropeens = in.readDouble();
        }
        if (in.readByte() == 0) {
            pdm_en_entrees_des_autres_films = null;
        } else {
            pdm_en_entrees_des_autres_films = in.readDouble();
        }
        if (in.readByte() == 0) {
            filmsArtEtEssai = null;
        } else {
            filmsArtEtEssai = in.readDouble();
        }
        if (in.readByte() == 0) {
            pdmEnEntreesDesFilmsArtEtEssai = null;
        } else {
            pdmEnEntreesDesFilmsArtEtEssai = in.readDouble();
        }
        latitude = in.readString();
        longitude = in.readString();
        geolocation = in.readParcelable(GeolocalisationParcelable.class.getClassLoader());
        distance = in.readDouble();
    }

    public static final Creator<CinemaParcelable> CREATOR = new Creator<>() {
        @Override
        public CinemaParcelable createFromParcel(Parcel in) {
            return new CinemaParcelable(in);
        }
        @Override
        public CinemaParcelable[] newArray(int size) {
            return new CinemaParcelable[size];
        }
    };

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public double getLatitude() {
        return Double.parseDouble(latitude);
    }

    public double getLongitude() {
        return Double.parseDouble(longitude);
    }

    public String getNom() {
        return name;
    }

    public String getAdresse() {
        return address;
    }



    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonString;
        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("toString()", e);
        }
        return jsonString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(this.regionCnc);
        dest.writeString(this.ndegAuto);
        dest.writeString(this.name);
        dest.writeString(this.region);
        dest.writeString(this.address);
        dest.writeString(this.inseeCode);
        dest.writeString(this.district);
        dest.writeDouble(this.districtPopulation);
        dest.writeString(this.dep);
        dest.writeString(this.ndegUu);
        dest.writeString(this.urbanUnity);
        dest.writeDouble(this.urbanUnityPopulation);
        dest.writeString(this.geographicalSituation);
        dest.writeValue(this.screens);
        dest.writeValue(this.seats);
        dest.writeValue(this.weekActivity);
        dest.writeValue(this.sessions);
        dest.writeValue(this.entries2022);
        dest.writeValue(this.entries2021);
        dest.writeValue(this.entriesUpgrade);
        dest.writeString(this.entryRange);
        dest.writeString(this.programmer);
        dest.writeString(this.ae);
        dest.writeString(this.categorieArtEtEssai);
        dest.writeString(this.labelArtEtEssai);
        dest.writeString(this.genre);
        dest.writeString(this.multiplexe);
        dest.writeString(this.zoneDeLaCommune);
        dest.writeValue(this.nombreDeFilmsProgrammes);
        dest.writeValue(this.nombreDeFilmsInedits);
        dest.writeValue(this.nombreDeFilmsEnSemaine1);
        dest.writeValue(this.pdmEnEntreesDesFilmsFrancais);
        dest.writeValue(this.pdmEnEntreesDesFilmsAmericains);
        dest.writeValue(this.pdmEnEntreesDesFilmsEuropeens);
        dest.writeValue(this.pdm_en_entrees_des_autres_films);
        dest.writeValue(this.filmsArtEtEssai);
        dest.writeValue(this.pdmEnEntreesDesFilmsArtEtEssai);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeParcelable(this.geolocation, flags);
        dest.writeDouble(this.distance);
    }

    public int getRegionCnc() {
        return regionCnc;
    }

    public void setRegionCnc(int regionCnc) {
        this.regionCnc = regionCnc;
    }

    public String getNdegAuto() {
        return ndegAuto;
    }

    public void setNdegAuto(String ndegAuto) {
        this.ndegAuto = ndegAuto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInseeCode() {
        return inseeCode;
    }

    public void setInseeCode(String inseeCode) {
        this.inseeCode = inseeCode;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public double getDistrictPopulation() {
        return districtPopulation;
    }

    public void setDistrictPopulation(double districtPopulation) {
        this.districtPopulation = districtPopulation;
    }

    public String getDep() {
        return dep;
    }

    public void setDep(String dep) {
        this.dep = dep;
    }

    public String getNdegUu() {
        return ndegUu;
    }

    public void setNdegUu(String ndegUu) {
        this.ndegUu = ndegUu;
    }

    public String getUrbanUnity() {
        return urbanUnity;
    }

    public void setUrbanUnity(String urbanUnity) {
        this.urbanUnity = urbanUnity;
    }

    public double getUrbanUnityPopulation() {
        return urbanUnityPopulation;
    }

    public void setUrbanUnityPopulation(double urbanUnityPopulation) {
        this.urbanUnityPopulation = urbanUnityPopulation;
    }

    public String getGeographicalSituation() {
        return geographicalSituation;
    }

    public void setGeographicalSituation(String geographicalSituation) {
        this.geographicalSituation = geographicalSituation;
    }

    public Double getScreens() {
        return screens;
    }

    public void setScreens(Double screens) {
        this.screens = screens;
    }

    public Double getSeats() {
        return seats;
    }

    public void setSeats(Double seats) {
        this.seats = seats;
    }

    public Double getWeekActivity() {
        return weekActivity;
    }

    public void setWeekActivity(Double weekActivity) {
        this.weekActivity = weekActivity;
    }

    public Double getSessions() {
        return sessions;
    }

    public void setSessions(Double sessions) {
        this.sessions = sessions;
    }

    public Double getEntries2022() {
        return entries2022;
    }

    public void setEntries2022(Double entries2022) {
        this.entries2022 = entries2022;
    }

    public Double getEntries2021() {
        return entries2021;
    }

    public void setEntries2021(Double entries2021) {
        this.entries2021 = entries2021;
    }

    public Double getEntriesUpgrade() {
        return entriesUpgrade;
    }

    public void setEntriesUpgrade(Double entriesUpgrade) {
        this.entriesUpgrade = entriesUpgrade;
    }

    public String getEntryRange() {
        return entryRange;
    }

    public void setEntryRange(String entryRange) {
        this.entryRange = entryRange;
    }

    public String getProgrammer() {
        return programmer;
    }

    public void setProgrammer(String programmer) {
        this.programmer = programmer;
    }

    public String getAe() {
        return ae;
    }

    public void setAe(String ae) {
        this.ae = ae;
    }

    public String getCategorieArtEtEssai() {
        return categorieArtEtEssai;
    }

    public void setCategorieArtEtEssai(String categorieArtEtEssai) {
        this.categorieArtEtEssai = categorieArtEtEssai;
    }

    public String getLabelArtEtEssai() {
        return labelArtEtEssai;
    }

    public void setLabelArtEtEssai(String labelArtEtEssai) {
        this.labelArtEtEssai = labelArtEtEssai;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getMultiplexe() {
        return multiplexe;
    }

    public void setMultiplexe(String multiplexe) {
        this.multiplexe = multiplexe;
    }

    public String getZoneDeLaCommune() {
        return zoneDeLaCommune;
    }

    public void setZoneDeLaCommune(String zoneDeLaCommune) {
        this.zoneDeLaCommune = zoneDeLaCommune;
    }

    public Double getNombreDeFilmsProgrammes() {
        return nombreDeFilmsProgrammes;
    }

    public void setNombreDeFilmsProgrammes(Double nombreDeFilmsProgrammes) {
        this.nombreDeFilmsProgrammes = nombreDeFilmsProgrammes;
    }

    public Double getNombreDeFilmsInedits() {
        return nombreDeFilmsInedits;
    }

    public void setNombreDeFilmsInedits(Double nombreDeFilmsInedits) {
        this.nombreDeFilmsInedits = nombreDeFilmsInedits;
    }

    public Double getNombreDeFilmsEnSemaine1() {
        return nombreDeFilmsEnSemaine1;
    }

    public void setNombreDeFilmsEnSemaine1(Double nombreDeFilmsEnSemaine1) {
        this.nombreDeFilmsEnSemaine1 = nombreDeFilmsEnSemaine1;
    }

    public Double getPdmEnEntreesDesFilmsFrancais() {
        return pdmEnEntreesDesFilmsFrancais;
    }

    public void setPdmEnEntreesDesFilmsFrancais(Double pdmEnEntreesDesFilmsFrancais) {
        this.pdmEnEntreesDesFilmsFrancais = pdmEnEntreesDesFilmsFrancais;
    }

    public Double getPdmEnEntreesDesFilmsAmericains() {
        return pdmEnEntreesDesFilmsAmericains;
    }

    public void setPdmEnEntreesDesFilmsAmericains(Double pdmEnEntreesDesFilmsAmericains) {
        this.pdmEnEntreesDesFilmsAmericains = pdmEnEntreesDesFilmsAmericains;
    }

    public Double getPdmEnEntreesDesFilmsEuropeens() {
        return pdmEnEntreesDesFilmsEuropeens;
    }

    public void setPdmEnEntreesDesFilmsEuropeens(Double pdmEnEntreesDesFilmsEuropeens) {
        this.pdmEnEntreesDesFilmsEuropeens = pdmEnEntreesDesFilmsEuropeens;
    }

    public Double getPdm_en_entrees_des_autres_films() {
        return pdm_en_entrees_des_autres_films;
    }

    public void setPdm_en_entrees_des_autres_films(Double pdm_en_entrees_des_autres_films) {
        this.pdm_en_entrees_des_autres_films = pdm_en_entrees_des_autres_films;
    }

    public Double getFilmsArtEtEssai() {
        return filmsArtEtEssai;
    }

    public void setFilmsArtEtEssai(Double filmsArtEtEssai) {
        this.filmsArtEtEssai = filmsArtEtEssai;
    }

    public Double getPdmEnEntreesDesFilmsArtEtEssai() {
        return pdmEnEntreesDesFilmsArtEtEssai;
    }

    public void setPdmEnEntreesDesFilmsArtEtEssai(Double pdmEnEntreesDesFilmsArtEtEssai) {
        this.pdmEnEntreesDesFilmsArtEtEssai = pdmEnEntreesDesFilmsArtEtEssai;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public GeolocalisationParcelable getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(GeolocalisationParcelable geolocation) {
        this.geolocation = geolocation;
    }

}
