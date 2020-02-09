package com.oim.punissement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.SQLException;

import com.oim.punissement.R;
import com.oim.punissement.beans.GroupeObject;

import java.util.ArrayList;

public class GroupeDAO extends DAO<GroupeObject> {

    private String Table_name = DBHelper.TABLE_GROUPE;
    private String[] allColumns = {
            DBHelper.ID,
            DBHelper.GROUPE_NOM
    };

    public GroupeDAO(Context context) {
        super(context);
    }

    /**
     * Fonction qui ajoute un Groupe
     * @param monBean : Groupe à ajouter
     */
    @Override
    public void insert(GroupeObject monBean) {
        // Content values contient les données qui seront insérées.
        // Son utilisation est obligatoire pour les requêtes.
        ContentValues content = new ContentValues();
        content.put(DBHelper.GROUPE_NOM, monBean.getNom());

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
     * Fonction de mise à jour d'un Groupe
     * @param monBean : Groupe à modifier
     */
    @Override
    public void update(GroupeObject monBean) {
        ContentValues content = new ContentValues();
        content.put(DBHelper.GROUPE_NOM, monBean.getNom());

        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete d'update, avec en paramètre de ma requete l'ID
        getBDD().update(Table_name, content,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction de suppression d'un Groupe
     * @param monBean : Groupe à supprimer
     */
    @Override
    public void delete(GroupeObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete delete, avec en paramètre de ma requete l'ID
        getBDD().delete(Table_name,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction qui récupère les Groupe
     * @return : liste de Groupe
     */
    @Override
    public ArrayList<GroupeObject> getAll() {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare une nouvelle liste
        ArrayList<GroupeObject> liste_groupes = new ArrayList<>();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns,
                null, null, null, null, DBHelper.GROUPE_NOM + " ASC");

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                GroupeObject groupe = new GroupeObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.GROUPE_NOM))
                );
                // Puis je l'ajoute à ma liste
                liste_groupes.add(groupe);
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois tous mes enregistrements récupérés, je peux fermer le curseur et retourner ma liste
        res.close();
        return liste_groupes;
    }

    /**
     * Fonction qui récupère un Groupe par son id
     * @param id : id dU Groupe à rechercher
     * @return : Groupe
     */
    @Override
    public GroupeObject getById(int id) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        GroupeObject groupe = new GroupeObject();
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
                groupe = new GroupeObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.GROUPE_NOM))
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Groupe
        res.close();
        return groupe;
    }

    /**
     * Fonction qui ajoute des données dans la table Groupe
     */
    @Override
    public void fillTable(Context context) {
        TypedArray ta = res.obtainTypedArray(R.array.groupe_test);
        int n = ta.length();
        String[] array = new String[n];

        for (int i = 0; i < n; ++i) {
            array[i] = ta.getString(i);
            insert(new GroupeObject(array[i]));
        }
        ta.recycle();
    }

    /**
     * Fonction qui recherche une Adresse par ses attributs
     * @param monBean : Adresse à rechercher
     * @return : Adresse
     */
    public GroupeObject getByName(GroupeObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        GroupeObject groupe = new GroupeObject();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns, DBHelper.GROUPE_NOM + " = ?",
                new String[] { monBean.getNom() }, null, null, null, null);

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                groupe = new GroupeObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.GROUPE_NOM))
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Groupe
        res.close();
        return groupe;
    }
}
