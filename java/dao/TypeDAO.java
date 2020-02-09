package com.oim.punissement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;

import com.oim.punissement.R;
import com.oim.punissement.beans.TypeObject;

public class TypeDAO extends DAO<TypeObject> {

    private String Table_name = DBHelper.TABLE_TYPE;
    private String[] allColumns = {
            DBHelper.ID,
            DBHelper.TYPE_NOM
    };

    public TypeDAO(Context context) {
        super(context);
    }

    /**
     * Fonction qui ajoute un Type
     * @param monBean : Type à ajouter
     */
    @Override
    public void insert(TypeObject monBean) {
        // Content values contient les données qui seront insérées.
        // Son utilisation est obligatoire pour les requêtes.
        ContentValues content = new ContentValues();
        content.put(DBHelper.TYPE_NOM, monBean.getNom());

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
     * Fonction de mise à jour d'un Type
     * @param monBean : Type à modifier
     */
    @Override
    public void update(TypeObject monBean) {
        ContentValues content = new ContentValues();
        content.put(DBHelper.TYPE_NOM, monBean.getNom());

        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete d'update, avec en paramètre de ma requete l'ID
        getBDD().update(Table_name, content,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction de suppression d'un Type
     * @param monBean : Type à supprimer
     */
    @Override
    public void delete(TypeObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete delete, avec en paramètre de ma requete l'ID
        getBDD().delete(Table_name,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction qui récupère les Types
     * @return : liste de Type
     */
    @Override
    public ArrayList<TypeObject> getAll() {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare une nouvelle liste
        ArrayList<TypeObject> liste_types = new ArrayList<>();
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
                TypeObject type = new TypeObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.TYPE_NOM))
                );
                // Puis je l'ajoute à ma liste
                liste_types.add(type);
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois tous mes enregistrements récupérés, je peux fermer le curseur et retourner ma liste
        res.close();
        return liste_types;
    }

    /**
     * Fonction qui récupère un Type par son id
     * @param id : id de l'ojet à rechercher
     * @return : Type
     */
    @Override
    public TypeObject getById(int id) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        TypeObject type = new TypeObject();
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
                type = new TypeObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.TYPE_NOM))
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Type
        res.close();
        return type;
    }

    /**
     * Fonction qui ajoute des données dans la table Type
     */
    @Override
    public void fillTable(Context context) {
        TypedArray ta = res.obtainTypedArray(R.array.type_test);
        int n = ta.length();
        String[] array = new String[n];

        for (int i = 0; i < n; ++i) {
            array[i] = ta.getString(i);
            insert(new TypeObject(array[i]));
        }
        ta.recycle();
    }

    /**
     * Fonction qui récupère un Type par son nom
     * @param monBean : Type à rechercher
     * @return : Type
     */
    public TypeObject getByName(TypeObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        TypeObject type = new TypeObject();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns, DBHelper.TYPE_NOM + " = ?",
                new String[] { monBean.getNom() }, null, null, null, null);

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                type = new TypeObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.TYPE_NOM))
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Type
        res.close();
        return type;
    }

}
