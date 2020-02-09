package com.oim.punissement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.database.SQLException;

import com.oim.punissement.R;
import com.oim.punissement.beans.AdresseObject;
import com.oim.punissement.beans.GroupeObject;
import com.oim.punissement.beans.PunitionObject;
import com.oim.punissement.beans.StagiaireObject;
import com.oim.punissement.beans.TypeObject;

import java.util.ArrayList;
import java.util.Random;

public class PunitionDAO extends DAO<PunitionObject> {

    private GroupeDAO groupeDAO;
    private StagiaireDAO stagiaireDAO;
    private AdresseDAO adresseDAO;
    private TypeDAO typeDAO;

    private String Table_name = DBHelper.TABLE_PUNITION;
    private String[] allColumns = {
            DBHelper.ID,
            DBHelper.PUNITION_DATE,
            DBHelper.PUNITION_ID_GROUPE,
            DBHelper.PUNITION_ID_STAGIAIRE,
            DBHelper.PUNITION_ID_ADRESSE,
            DBHelper.PUNITION_ID_TYPE
    };

    public PunitionDAO(Context context) {
        super(context);
        groupeDAO    = new GroupeDAO(context);
        stagiaireDAO = new StagiaireDAO(context);
        adresseDAO   = new AdresseDAO(context);
        typeDAO      = new TypeDAO(context);
    }

    /**
     * Fonction qui ajoute une Punition
     * @param monBean : Punition à ajouter
     */
    @Override
    public void insert(PunitionObject monBean) {
        // Verifier si l'adresse existe dans la BDD
        AdresseObject verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());
        // Si non l'ajouter et récupérer son id
        if (verifAdresse.getId() == 0) {
            adresseDAO.insert(monBean.getAdresseObject());
            verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());
        }
        // On récupère l'ID du type
        TypeObject type = typeDAO.getByName(monBean.getTypeObject());

        // Content values contient les données qui seront insérées.
        // Son utilisation est obligatoire pour les requêtes.
        ContentValues content = new ContentValues();
        content.put(DBHelper.PUNITION_DATE ,        monBean.getDate());
        content.put(DBHelper.PUNITION_ID_GROUPE,    monBean.getGroupeObject().getId());
        content.put(DBHelper.PUNITION_ID_STAGIAIRE, monBean.getStagiaireObject().getId());
        content.put(DBHelper.PUNITION_ID_ADRESSE,   verifAdresse.getId());
        content.put(DBHelper.PUNITION_ID_TYPE,      type.getId());

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
     * Fonction de mise à jour d'une Punition
     * @param monBean : Punition à modifier
     */
    @Override
    public void update(PunitionObject monBean) {
        // Verifier si l'adresse existe dans la BDD
        AdresseObject verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());
        // Si non l'ajouter et récupérer son id
        if (verifAdresse.getId() == 0) {
            adresseDAO.insert(monBean.getAdresseObject());
            verifAdresse = adresseDAO.getByName(monBean.getAdresseObject());
        }
        // On récupère l'ID du type
        TypeObject type = typeDAO.getByName(monBean.getTypeObject());

        ContentValues content = new ContentValues();
        content.put(DBHelper.PUNITION_DATE ,        monBean.getDate());
        content.put(DBHelper.PUNITION_ID_GROUPE,    monBean.getGroupeObject().getId());
        content.put(DBHelper.PUNITION_ID_STAGIAIRE, monBean.getStagiaireObject().getId());
        content.put(DBHelper.PUNITION_ID_ADRESSE,   verifAdresse.getId());
        content.put(DBHelper.PUNITION_ID_TYPE,      type.getId());

        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete d'update, avec en paramètre de ma requete l'ID
        getBDD().update(Table_name, content,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction de suppression d'une Punition
     * @param monBean : Punition à supprimer
     */
    @Override
    public void delete(PunitionObject monBean) {
        // Ouverture de la connexion à la BDD
        open();
        // Je lance ma requete delete, avec en paramètre de ma requete l'ID
        getBDD().delete(Table_name,
                DBHelper.ID + " = ?", new String[] { String.valueOf(monBean.getId())} );
        // Fermeture de la connexion à la BDD
        close();
    }

    /**
     * Fonction qui récupère les Punition
     * @return : liste de Punition
     */
    @Override
    public ArrayList<PunitionObject> getAll() {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare une nouvelle liste
        ArrayList<PunitionObject> liste_punitions = new ArrayList<>();
        // J'exécute ma requête et "stocke" le resultat de le curseur
        // (Le curseur me permettra d'indexer tous les éléments retournés par ma requete)
        Cursor res = getBDD().query(Table_name, allColumns,
                null, null, null, null, DBHelper.PUNITION_DATE + " ASC");

        // Ici je teste si ma table a bien des enregistrements
        if(res.getCount() != 0) {
            res.moveToFirst();
            // Puis tant qu'il y a des enregistrements
            while(!res.isAfterLast()) {
                // Ouverture des connexions
                groupeDAO.open();
                stagiaireDAO.open();
                adresseDAO.open();
                typeDAO.open();
                //On instancie notre objet Groupe
                GroupeObject groupe = groupeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_GROUPE)));
                //On instancie notre objet Stagiaire
                StagiaireObject stagiaire = stagiaireDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_STAGIAIRE)));
                //On instancie notre objet Adresse
                AdresseObject adresse = adresseDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_ADRESSE)));
                //On instancie notre objet Type
                TypeObject type = typeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_TYPE)));
                // Fermeture des connexions
                groupeDAO.close();
                stagiaireDAO.close();
                adresseDAO.close();
                typeDAO.close();
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                PunitionObject punition = new PunitionObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.PUNITION_DATE)),
                        groupe,
                        stagiaire,
                        adresse,
                        type
                );
                // Puis je l'ajoute à ma liste
                liste_punitions.add(punition);
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois tous mes enregistrements récupérés, je peux fermer le curseur et retourner ma liste
        res.close();
        return liste_punitions;
    }

    /**
     * Fonction qui récupère une Punition par son id
     * @param id : id de la Punition à rechercher
     * @return : Punition
     */
    @Override
    public PunitionObject getById(int id) {
        // Ouverture de la connexion à la BDD
        open();
        // Je déclare mon type
        PunitionObject punition = new PunitionObject();
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
                stagiaireDAO.open();
                adresseDAO.open();
                typeDAO.open();
                //On instancie notre objet Groupe
                GroupeObject groupe = groupeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_GROUPE)));
                //On instancie notre objet Stagiaire
                StagiaireObject stagiaire = stagiaireDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_STAGIAIRE)));
                //On instancie notre objet Adresse
                AdresseObject adresse = adresseDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_ADRESSE)));
                //On instancie notre objet Type
                TypeObject type = typeDAO.getById(res.getInt(res.getColumnIndex(DBHelper.PUNITION_ID_TYPE)));
                // Fermeture des connexions
                groupeDAO.close();
                stagiaireDAO.close();
                adresseDAO.close();
                typeDAO.close();
                // J'instancie un objet TypeObject avec l'enregistrement en cours de lecture
                punition = new PunitionObject(
                        res.getInt(res.getColumnIndex(DBHelper.ID)),
                        res.getString(res.getColumnIndex(DBHelper.PUNITION_DATE)),
                        groupe,
                        stagiaire,
                        adresse,
                        type
                );
                // Et je déplace le curseur sur l'enregistrement suivant.
                res.moveToNext();
            }
        }
        // Fermeture de la connexion à la BDD
        close();
        // Une fois mon enregistrement récupéré, je peux fermer le curseur et retourner mon Groupe
        res.close();
        return punition;
    }

    /**
     * Fonction qui ajoute des données dans la table
     */
    @Override
    public void fillTable(Context context) {
        // Ouverture des connexions
        groupeDAO.open();
        stagiaireDAO.open();
        adresseDAO.open();
        typeDAO.open();
        ArrayList<GroupeObject> liste_groupes       = groupeDAO.getAll();
        ArrayList<StagiaireObject> liste_stagiaires = stagiaireDAO.getAll();
        ArrayList<AdresseObject> liste_adresses     = adresseDAO.getAll();
        ArrayList<TypeObject> liste_types           = typeDAO.getAll();
        // Fermeture des connexions
        groupeDAO.close();
        stagiaireDAO.close();
        adresseDAO.close();
        typeDAO.close();
        if (!liste_groupes.isEmpty() && !liste_stagiaires.isEmpty()
                && !liste_adresses.isEmpty() && !liste_types.isEmpty()) {
            Random rand = new Random();
            TypedArray ta = res.obtainTypedArray(R.array.punition_test);
            int n = ta.length();
            String[] array = new String[n];

            for (int i = 0; i < n; ++i) {
                int groupeAleatoire    = rand.nextInt(liste_groupes.size());
                int stagiaireAleatoire = rand.nextInt(liste_stagiaires.size());
                int adresseAleatoire   = rand.nextInt(liste_adresses.size());
                int typeAleatoire      = rand.nextInt(liste_types.size());
                array[i] = ta.getString(i);
                insert(new PunitionObject(
                        array[i],
                        liste_groupes.get(groupeAleatoire),
                        liste_stagiaires.get(stagiaireAleatoire),
                        liste_adresses.get(adresseAleatoire),
                        liste_types.get(typeAleatoire))
                );
            }
            ta.recycle();
        }
    }

}
