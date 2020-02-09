package com.oim.punissement.utilitaires;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormStagiaireCheckup {

    private String Erreur;

    public String CheckForm (String nom, String prenom, String url, String email,
                             String adresseNum, String adresseName,
                             String adresseCP, String adresseCity) {
        // Contiendra les erreur du formulaire (s'il y en a)
        Erreur = "";
        // On fait les érification de tous les champs
        CheckNom(nom);
        CheckPrenom(prenom);
        CheckUrl(url);
        CheckMail(email);
        CheckNumRueAdresse(adresseNum);
        CheckNomRueAdresse(adresseName);
        CheckCodePostalAdresse(adresseCP);
        CheckVilleAdresse(adresseCity);

        return Erreur;
    }

    /**
     * Vérifie le champ Nom
     * @param nom : champ à vérifier
     */
    private void CheckNom (String nom) {
        if(nom.isEmpty()) {
            Erreur = Erreur + "NOM vide\n";
        } else {
            if (nom.matches("[^0-9]*[0-9]+[^0-9]*")) {
                Erreur = Erreur + "champ NOM comporte des caractères incorects\n";
            }
        }
    }

    /**
     * Vérifie le champ Prenom
     * @param prenom : champ à vérifier
     */
    private void CheckPrenom (String prenom) {
        if (prenom.isEmpty()) {
            Erreur = Erreur + "PRENOM vide\n";
        } else {
            if (prenom.matches("[^0-9]*[0-9]+[^0-9]*")) {
                Erreur = Erreur + "champ PRENOM comporte des caractères incorects\n";
            }
        }
    }

    /**
     * Vérifie le champ Url
     * @param url : champ à vérifier
     */
    private void CheckUrl (String url) {

    }

    /**
     * Vérifie le champ Mail
     * @param email : champ à vérifier
     */
    private void CheckMail (String email) {
        if (email.isEmpty()) {
            Erreur = Erreur + "EMAIL vide\n";
        } else {
            if (!email.matches("([^.@]+)([.][^.@]+)*@([^.@]+[.])+([^.@]{2,6})")) {
                Erreur = Erreur + "champ EMAIL format incorect\n";
            }
        }
    }

    /**
     * Vérifie le champ Numéro de la rue
     * @param adresseNum : champ à vérifier
     */
    private void CheckNumRueAdresse (String adresseNum) {
        if (adresseNum.isEmpty()) {
            Erreur = Erreur + "NUMERO DE RUE vide\n";
        } else {
            if(!adresseNum.matches("\\d+")){
                Erreur = Erreur + "Veuillez saisir le numero de voie en chiffre\n";
            }
        }
    }

    /**
     * Vérifie le champ Nom de rue
     * @param adresseName : champ à vérifier
     */
    private void CheckNomRueAdresse (String adresseName) {
        boolean checked = false;
        String [] listeVoie = {"rue", "place", "chemin", "allée", "avenue", "impasse", "carrefour", "hall", "boulevard", "voie"};
        Pattern pattern;
        Matcher matcher;

        if (adresseName.isEmpty()) {
            Erreur = Erreur + "NOM DE LA RUE vide\n";
        } else {
            // On vérifie si dans la chaine de caractère adresseName contient un des mots cité dans listeVoie
            for (String voie : listeVoie) {
                pattern = Pattern.compile(voie);
                matcher = pattern.matcher(adresseName.toLowerCase());
                if (matcher.find()) {
                    checked = true;
                    break;
                }
            }
            // Si le nom d'une voie n'a pas été trouvée
            if (!checked) {
                Erreur = Erreur + "Veuillez spécifier un nom de voie correct\n";
            }
        }
    }

    /**
     * Vérifie le champ Code postal de la ville
     * @param adresseCP : champ à vérifier
     */
    private void CheckCodePostalAdresse (String adresseCP) {
        if (adresseCP.isEmpty()) {
            Erreur = Erreur + "CODE POSTAL vide\n";
        } else {
            // On vérifie si les champs numero et CP contiennent bien que des nombres et que la taille de CP soit bien inférieur ou égale à 5
            if (!adresseCP.matches("\\d+")) {
                Erreur = Erreur + "Veuillez saisir le code postal en chiffre\n";
            }
        }
    }

    /**
     * Vérifie le champ Nom de la ville
     * @param adresseCity : champ à vérifier
     */
    private void CheckVilleAdresse (String adresseCity) {
        if (adresseCity.isEmpty()) {
            Erreur = Erreur + "NOM DE VILLE vide\n";
        } else {
            if (adresseCity.matches("[^0-9]*[0-9]+[^0-9]*")) {
                Erreur = Erreur + "Nom de ville comporte des caractères incorrects\n";
            }
        }
    }

}
