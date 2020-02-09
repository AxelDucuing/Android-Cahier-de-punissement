package com.oim.punissement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.SQLException;

import com.oim.punissement.R;
import com.oim.punissement.beans.AdresseObject;
import com.oim.punissement.beans.GroupeObject;
import com.oim.punissement.beans.StagiaireObject;

import java.util.ArrayList;
import java.util.Random;

public class StagiaireDAO extends DAO<StagiaireObject> {

    private GroupeDAO groupeDAO;
    private AdresseDAO adresseDAO;

    private String Table_name = DBHelper.TABLE_STAGIAIRE;
    private String[] allColumns = {
            DBHelper.ID,
            DBHelper.STAGIAIRE_NOM,
            DBHelper.STAGIAIRE_PRENOM,
            DBHelper.STAGIAIRE_URL,
            DBHelper.STAGIAIRE_MAIL,
            DBHelper.STAGIAIRE_ID_GROUPE,
            DBHelper.STAGIAIRE_ID_ADRESSE
    };

    public StagiaireDAO(Context context) {
        super(context);
        groupeDAO   = new GroupeDAO(context);
        adresseDAO  = new AdresseDAO(context);
    }

    /**
     * Fonction qui ajoute un Stagiaire
     * @param monBean : Stagiaire à ajouter
     */
    @Override
    public void insert(StagiaireObject monBean) {
        // On récupère l'ID du groupe
        GroupeObject groupe = groupeDAO.getByName(monBean.getGroupeObject());

        // Verifier si l'adresse existe dans la BDD
        AdresseObject verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());

        // Si non l'ajouter et récupérer son id
        if (verifAdresse.getId() == 0) {
            adresseDAO.insert(monBean.getAdresseObject());
            verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());
        }

        // Content values contient les données qui seront insérées.
        // Son utilisation est obligatoire pour les requêtes.
        ContentValues content = new ContentValues();
        content.put(DBHelper.STAGIAIRE_NOM,        monBean.getNom());
        content.put(DBHelper.STAGIAIRE_PRENOM,     monBean.getPrenom());
        content.put(DBHelper.STAGIAIRE_URL,        monBean.getUrl());
        content.put(DBHelper.STAGIAIRE_MAIL,       monBean.getMail());
        content.put(DBHelper.STAGIAIRE_ID_GROUPE,  groupe.getId());
        content.put(DBHelper.STAGIAIRE_ID_ADRESSE, verifAdresse.getId());

        // Ici j'utilise insertOrThrow qui me permet de dégager une exception si l'insert ne s'est pas bien passé.
        try {
            // Ouverture de la connexion à la BDD
            open();
            getBDD().insertOrThrow(Table_name, null, content);
            // Fermeture de la connexion à la BDD
            close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fonction de mise à jour d'un Stagiaire
     * @param monBean : Stagiaire à modifier
     */
    @Override
    public void update(StagiaireObject monBean) {
        // On récupère l'ID du groupe
        GroupeObject groupe = groupeDAO.getByName(monBean.getGroupeObject());

        // Verifier si l'adresse existe dans la BDD
        AdresseObject verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());


        // Si non l'ajouter et récupérer son id
        if (verifAdresse.getId() == 0) {
            adresseDAO.insert(monBean.getAdresseObject());
            verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());
        }

        ContentValues content = new ContentValues();
        content.put(DBHelper.STAGIAIRE_NOM,        monBean.getNom());
        content.put(DBHelper.STAGIAIRE_PRENOM,     monBean.getPrenom());
        content.put(DBHelper.STAGIAIRE_URL,        monBean.getUrl());
        content.put(DBHelper.STAGIAIRE_MAIL,       monBean.getMail());
        content.put(DBHelper.STAGIAIRE_ID_GROUPE,  groupe.getId());
        content.put(DBHelper.STAGIAIRE_ID_ADRESSE, verifAdresse.getId());

        try {
            // Ouverture de la connexion à la BDD
            open();
            getBDD().update(Table_name, content,
                    DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
            System.out.println(getBDD().update(Table_name, content,
                    DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} ));
            // Fermeture de la connexion à la BDD
            close();
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Fonction de suppression d'un Stagiaire
     * @param monBean : Stagiaire à supprimer
     */
    @Override
    public void delete(StagiaireObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete delete, avec en paramètre de ma requete l'ID
        getBDD().delete(Table_name,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction qui récupère les Stagiaires
     * @return : liste de Stagiaires
     */
    @Override
    public ArrayList<StagiaireObject> getAll() {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare une nouvelle liste
        ArrayList<StagiaireObject> liste_stagiaires = new ArrayList<>();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns,
                null, null, null, null, DBHelper.STAGIAIRE_NOM + " ASC");

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // Ouverture des connexions
                groupeDAO.open();
                adresseDAO.open();
                //On instancie notre objet Groupe
                GroupeObject groupe = groupeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_GROUPE)));
                //On instancie notre objet Adresse
                AdresseObject adresse = adresseDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_ADRESSE)));
                // Fermeture des connexions
                groupeDAO.close();
                adresseDAO.close();
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                StagiaireObject stagiaire = new StagiaireObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_NOM)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_PRENOM)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_URL)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_MAIL)),
                        groupe,
                        adresse
                );
                // Puis je l'ajoute à ma liste
                liste_stagiaires.add(stagiaire);
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois tous mes enregistrements récupérés, je peux fermer le curseur et retourner ma liste
        res.close();
        return liste_stagiaires;
    }

    /**
     * Fonction qui récupère un Stagiaire par son id
     * @param id : id du Stagiaire à rechercher
     * @return : Stagiaire
     */
    @Override
    public StagiaireObject getById(int id) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        StagiaireObject stagiaire = new StagiaireObject();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns, DBHelper.ID + " = ?",
                new String[] { String.valueOf(id)}, null, null, null, null);

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // Ouverture des connexions
                groupeDAO.open();
                adresseDAO.open();
                //On instancie notre objet Groupe
                GroupeObject groupe = groupeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_GROUPE)));
                //On instancie notre objet Adresse
                AdresseObject adresse = adresseDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_ADRESSE)));
                // Fermeture des connexions
                groupeDAO.close();
                adresseDAO.close();
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                stagiaire = new StagiaireObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_NOM)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_PRENOM)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_URL)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_MAIL)),
                        groupe,
                        adresse
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Groupe
        res.close();
        return stagiaire;
    }

    /**
     * Fonction qui ajoute des données dans la table Stagiaire
     */
    @Override
    public void fillTable(Context context) {
        // Ouverture des connexions
        groupeDAO.open();
        adresseDAO.open();
        ArrayList<GroupeObject> liste_groupes   = groupeDAO.getAll();
        ArrayList<AdresseObject> liste_adresses = adresseDAO.getAll();
        // Fermeture des connexions
        groupeDAO.close();
        adresseDAO.close();
        if (!liste_groupes.isEmpty() && !liste_adresses.isEmpty()) {
            Random rand = new Random();
            TypedArray ta = res.obtainTypedArray(R.array.stagiaire_test);
            int n = ta.length();
            String[][] array = new String[n][];

            for (int i = 0; i < n; ++i) {
                int groupeAleatoire = rand.nextInt(liste_groupes.size());
                int adresseAleatoire = rand.nextInt(liste_adresses.size());
                int id = ta.getResourceId(i, 0);
                if (id > 0) {
                    array[i] = res.getStringArray(id);
                    insert(new StagiaireObject(
                            array[i][0],
                            array[i][1],
                            array[i][2],
                            array[i][3],
                            liste_groupes.get(groupeAleatoire),
                            liste_adresses.get(adresseAleatoire))
                    );
                }
            }
            ta.recycle();
        }
    }

    /**
     * Fonction qui récupère les Stagiaires d'un groupe
     * @param id : id du groupe
     * @return   : liste de Stagiaires
     */
    public ArrayList<StagiaireObject> getAllByGroupe(int id) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare une nouvelle liste
        ArrayList<StagiaireObject> liste_stagiaires = new ArrayList<>();
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns, DBHelper.STAGIAIRE_ID_GROUPE + " = ?",
                new String[] { String.valueOf(id)}, null, null, DBHelper.STAGIAIRE_NOM + " ASC", null);
        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // Ouverture des connexions
                groupeDAO.open();
                adresseDAO.open();
                //On instancie notre objet Groupe
                GroupeObject groupe = groupeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.STAGIAIRE_ID_GROUPE)));
                //On instancie notre objet Adresse
                AdresseObject adresse = adresseDAO.getById(res.getInt(res.getColumnIndex(DBHelper.STAGIAIRE_ID_ADRESSE)));
                // Fermeture des connexions
                groupeDAO.close();
                adresseDAO.close();
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                StagiaireObject stagiaire = new StagiaireObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_NOM)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_PRENOM)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_URL)),
                        res.getString(res.getColumnIndex(DBHelper.STAGIAIRE_MAIL)),
                        groupe,
                        adresse
                );
                // Puis je l'ajoute à ma liste
                liste_stagiaires.add(stagiaire);
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois tous mes enregistrements récupérés, je peux fermer le curseur et retourner ma liste
        res.close();
        return liste_stagiaires;
    }
}
