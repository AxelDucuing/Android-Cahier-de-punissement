package com.oim.punissement.dao;

import android.content.Context;
import android.content.res.Resources;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public abstract class DAO<MonType> {

    Resources res;

    private DBHelper monDBHelper;

    // Je déclare mes méthodes d'accès à la BDD (pour ne pas avoir a le retaper sur chaque fonction
    private SQLiteDatabase bdd;

    DAO (Context context) {
        monDBHelper = new DBHelper(context);
        res = context.getResources();
    }

    void open() throws SQLException {
        bdd = monDBHelper.getWritableDatabase();
    }

    void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    /**
     * Fonction qui ajoute un Objet d'une une table
     * @param monBean : Objet à ajouter
     */
    public abstract void insert(MonType monBean);

    /**
     * Fonction de mise à jour d'un Objet d'une une table
     * @param monBean : Objet à modifier
     */
    public abstract void update(MonType monBean);

    /**
     * Fonction de suppression d'un Objet d'une table
     * @param monBean : Objet à supprimer
     */
    public abstract void delete(MonType monBean);

    /**
     * Fonction qui récupère les Objets d'une table
     * @return : liste d'Objets
     */
    public abstract ArrayList<MonType> getAll();

    /**
     * Fonction qui récupère un Objet par son id d'une table
     * @param id : id de l'ojet à rechercher
     * @return   : Objet
     */
    public abstract MonType getById(int id);

    /**
     *  Fonction qui ajoute des données dans la table
     */
    public abstract void fillTable(Context context);
}
