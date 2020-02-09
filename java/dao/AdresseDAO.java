package com.oim.punissement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.SQLException;

import com.oim.punissement.R;
import com.oim.punissement.beans.AdresseObject;

import java.util.ArrayList;

public class AdresseDAO extends DAO<AdresseObject> {

    private String Table_name = DBHelper.TABLE_ADRESSE;
    private String[] allColumns = {
            DBHelper.ID,
            DBHelper.ADRESSE_NUMERO,
            DBHelper.ADRESSE_NOM,
            DBHelper.ADRESSE_VILLE,
            DBHelper.ADRESSE_CP
    };

    public AdresseDAO(Context context) {
        super(context);
    }

    /**
     * Fonction qui ajoute une Adresse
     * @param monBean : Adresse à ajouter
     */
    @Override
    public void insert(AdresseObject monBean) {
        // Content values contient les données qui seront insérées.
        // Son utilisation est obligatoire pour les requêtes.
        ContentValues content = new ContentValues();
        content.put(DBHelper.ADRESSE_NUMERO, monBean.getNumero_rue());
        content.put(DBHelper.ADRESSE_NOM,    monBean.getNom_rue());
        content.put(DBHelper.ADRESSE_VILLE,  monBean.getVille());
        content.put(DBHelper.ADRESSE_CP,     monBean.getCp());

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
     * Fonction de mise à jour d'une Adresse
     * @param monBean : Adresse à modifier
     */
    @Override
    public void update(AdresseObject monBean) {
        ContentValues content = new ContentValues();
        content.put(DBHelper.ADRESSE_NUMERO, monBean.getNumero_rue());
        content.put(DBHelper.ADRESSE_NOM,    monBean.getNom_rue());
        content.put(DBHelper.ADRESSE_VILLE,  monBean.getVille());
        content.put(DBHelper.ADRESSE_CP,     monBean.getCp());

        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete d'update, avec en paramètre de ma requete l'ID
        getBDD().update(Table_name, content,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction de suppression d'une Adresse
     * @param monBean : Adresse à supprimer
     */
    @Override
    public void delete(AdresseObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete delete, avec en paramètre de ma requete l'ID
        getBDD().delete(Table_name,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction qui récupère les Adresses
     * @return : liste d'Adresses
     */
    @Override
    public ArrayList<AdresseObject> getAll() {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare une nouvelle liste
        ArrayList<AdresseObject> liste_adresses = new ArrayList<>();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns,
                null, null, null, null, null);

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                AdresseObject adresse = new AdresseObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getInt(res.getColumnIndex(DBHelper.ADRESSE_NUMERO)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_NOM)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_VILLE)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_CP))
                );
                // Puis je l'ajoute à ma liste
                liste_adresses.add(adresse);
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois tous mes enregistrements récupérés, je peux fermer le curseur et retourner ma liste
        res.close();
        return liste_adresses;
    }

    /**
     * Fonction qui récupère une Adresse par son id
     * @param id : id d'une Adresse à rechercher
     * @return : Adresse
     */
    @Override
    public AdresseObject getById(int id) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        AdresseObject adresse = new AdresseObject();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns, DBHelper.ID + " = ?",
                new String[] { String.valueOf(id)}, null, null, null, null);

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                adresse = new AdresseObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getInt(res.getColumnIndex(DBHelper.ADRESSE_NUMERO)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_NOM)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_VILLE)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_CP))
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Adresse
        res.close();
        return adresse;
    }

    /**
     * Fonction qui ajoute des données dans la table Adresse
     */
    @Override
    public void fillTable(Context context) {
        TypedArray ta = res.obtainTypedArray(R.array.adresse_test);
        int n = ta.length();
        String[][] array = new String[n][];

        for (int i = 0; i < n; ++i) {
            int id = ta.getResourceId(i, 0);
            if (id > 0) {
                array[i] = res.getStringArray(id);
                insert(new AdresseObject(Integer.parseInt(array[i][0]), array[i][1], array[i][2], array[i][3]));
            }
        }
        ta.recycle();
    }

    /**
     * Fonction qui recherche une Adresse par ses attributs
     * @param monBean : Adresse à rechercher
     * @return : Adresse
     */
    public AdresseObject getByName(AdresseObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        AdresseObject adresse = new AdresseObject();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns,
                DBHelper.ADRESSE_NUMERO + " = ? AND " + DBHelper.ADRESSE_NOM + " = ? AND " +
                         DBHelper.ADRESSE_CP + " = ? AND " +  DBHelper.ADRESSE_VILLE + " = ?",
                     new String[] { String.valueOf(monBean.getNumero_rue()), monBean.getNom_rue() ,monBean.getCp(), monBean.getVille()},
                null, null, null, null);
        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                adresse = new AdresseObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getInt(res.getColumnIndex(DBHelper.ADRESSE_NUMERO)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_NOM)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_VILLE)),
                        res.getString(res.getColumnIndex(DBHelper.ADRESSE_CP))
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Adresse
        res.close();
        return adresse;
    }
}
