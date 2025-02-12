/**
 * Projet: R411 - Projet filrouge - Equipe A
 * Auteurs: BONJOUR Corentin, FARCHETTO Lilian, HAMBLI Kenzo
 */
package edu.equipe_a.kcine.entries;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Classe User
 */
public class User {

    // Identifiant de l'utilisateur
    @JsonProperty("id")
    private String id;

    // Prénom de l'utilisateur
    @JsonProperty("firstName")
    private String firstName;

    // Nom de l'utilisateur
    @JsonProperty("lastName")
    private String lastName;

    // Adresse email de l'utilisateur
    @JsonProperty("email")
    private String email;

    // Sel de l'utilisateur
    @JsonProperty("salt")
    private String salt;

    // Mot de passe hashé de l'utilisateur
    @JsonProperty("hashedPassword")
    private String hashedPassword;

    /**
     * Getter de l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter du prénom de l'utilisateur
     * @return le prénom de l'utilisateur
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Getter du nom de l'utilisateur
     * @return le nom de l'utilisateur
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Getter de l'adresse email de l'utilisateur
     * @return l'adresse email de l'utilisateur
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Getter du sel de l'utilisateur
     * @return le sel de l'utilisateur
     */
    public String getSalt() {
        return this.salt;
    }

    /**
     * Getter du mot de passe hashé de l'utilisateur
     * @return le mot de passe hashé de l'utilisateur
     */
    public String getHashedPassword() {
        return this.hashedPassword;
    }

}
