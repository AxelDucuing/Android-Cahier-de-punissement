package com.oim.punissement.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedHashMap;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "punissement";
    private static final int DATABASE_VERSION = 9;

    static final String ID = "id";

    /** TABLE ADRESSE **/
    static final String TABLE_ADRESSE  = "adresse";
    static final String ADRESSE_NUMERO = "numero_rue";
    static final String ADRESSE_NOM    = "nom_rue";
    static final String ADRESSE_CP     = "cp";
    static final String ADRESSE_VILLE  = "ville";

    private static final LinkedHashMap<String, String> ADRESSE_FIELDS = new LinkedHashMap<String, String>() { {
        put(ADRESSE_NUMERO, "TEXT");
        put(ADRESSE_NOM,    "TEXT");
        put(ADRESSE_CP,     "TEXT");
        put(ADRESSE_VILLE,  "TEXT");
    }};

    /** TABLE GROUPE **/
    static final String TABLE_GROUPE  = "groupe";
    static final String GROUPE_NOM    = "nom";

    private static final LinkedHashMap<String, String> GROUPE_FIELDS = new LinkedHashMap<String, String>() { {
        put(GROUPE_NOM, "TEXT");
    }};

    /** TABLE STAGIAIRE **/
    static final String TABLE_STAGIAIRE       = "stagiaire";
    static final String STAGIAIRE_NOM         = "nom";
    static final String STAGIAIRE_PRENOM      = "prenom";
    static final String STAGIAIRE_URL         = "url";
    static final String STAGIAIRE_MAIL        = "mail";
    static final String STAGIAIRE_ID_GROUPE   = "id_groupe";
    static final String STAGIAIRE_ID_ADRESSE  = "id_adresse";
    private static final String STAGIAIRE_FOREIN_ID_GROUPE  = "FOREIGN KEY (" + STAGIAIRE_ID_GROUPE + ")";
    private static final String STAGIAIRE_FOREIN_ID_ADRESSE = "FOREIGN KEY (" + STAGIAIRE_ID_ADRESSE + ")";

    private static final LinkedHashMap<String, String> STAGIAIRE_FIELDS = new LinkedHashMap<String, String>() { {
        put(STAGIAIRE_NOM,    "TEXT");
        put(STAGIAIRE_PRENOM, "TEXT");
        put(STAGIAIRE_URL,    "TEXT");
        put(STAGIAIRE_MAIL,   "TEXT");
        put(STAGIAIRE_ID_GROUPE,  "INTEGER");
        put(STAGIAIRE_ID_ADRESSE, "INTEGER");
        put(STAGIAIRE_FOREIN_ID_GROUPE,  "REFERENCES " + TABLE_GROUPE + "(" + ID + ")");
        put(STAGIAIRE_FOREIN_ID_ADRESSE, "REFERENCES " + TABLE_ADRESSE + "(" + ID + ")");
    }};

    /** TABLE TYPE **/
    static final String TABLE_TYPE  = "type";
    static final String TYPE_NOM    = "nom";

    private static final LinkedHashMap<String, String> TYPE_FIELDS = new LinkedHashMap<String, String>() { {
        put(TYPE_NOM, "TEXT");
    }};

    /** TABLE PUNITION **/
    static final String TABLE_PUNITION         = "punition";
    static final String PUNITION_DATE          = "date";
    static final String PUNITION_ID_GROUPE     = "id_groupe";
    static final String PUNITION_ID_STAGIAIRE  = "id_stagiaire";
    static final String PUNITION_ID_ADRESSE    = "id_adresse";
    static final String PUNITION_ID_TYPE       = "id_type";
    private static final String PUNITION_FOREIN_ID_GROUPE     = "FOREIGN KEY (" + PUNITION_ID_GROUPE + ")";
    private static final String PUNITION_FOREIN_ID_STAGIAIRE  = "FOREIGN KEY (" + PUNITION_ID_STAGIAIRE + ")";
    private static final String PUNITION_FOREIN_ID_ADRESSE    = "FOREIGN KEY (" + PUNITION_ID_ADRESSE + ")";
    private static final String PUNITION_FOREIN_ID_TYPE       = "FOREIGN KEY (" + PUNITION_ID_TYPE + ")";

    private static final LinkedHashMap<String, String> PUNITION_FIELDS = new LinkedHashMap<String, String>() { {
        put(PUNITION_DATE,    "TEXT");
        put(PUNITION_ID_GROUPE,    "INTEGER");
        put(PUNITION_ID_STAGIAIRE, "INTEGER");
        put(PUNITION_ID_ADRESSE,   "INTEGER");
        put(PUNITION_ID_TYPE,      "INTEGER");
        put(PUNITION_FOREIN_ID_GROUPE,    "REFERENCES " + TABLE_GROUPE + "(" + ID + ")");
        put(PUNITION_FOREIN_ID_STAGIAIRE, "REFERENCES " + TABLE_STAGIAIRE + "(" + ID + ")");
        put(PUNITION_FOREIN_ID_ADRESSE,   "REFERENCES " + TABLE_ADRESSE + "(" + ID + ")");
        put(PUNITION_FOREIN_ID_TYPE,      "REFERENCES " + TABLE_TYPE + "(" + ID + ")");
    }};

    DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, TABLE_ADRESSE,   ADRESSE_FIELDS   );
        createTable(db, TABLE_GROUPE,    GROUPE_FIELDS    );
        createTable(db, TABLE_STAGIAIRE, STAGIAIRE_FIELDS );
        createTable(db, TABLE_TYPE,      TYPE_FIELDS      );
        createTable(db, TABLE_PUNITION,  PUNITION_FIELDS  );
    }

    /**
     * Cette fonction créé la table demandée
     * @param db        : base de donnée
     * @param tableName : nom de la table
     * @param fields    : nom des champs de la table
     */
    static private void createTable(SQLiteDatabase db, String tableName, LinkedHashMap<String, String> fields) {
        String command = "CREATE TABLE IF NOT EXISTS " + tableName + " ( " + ID + " INTEGER PRIMARY KEY AUTOINCREMENT";

        for (LinkedHashMap.Entry<String, String> entry : fields.entrySet())
            command = command + ", " + entry.getKey() + " " + entry.getValue();

        command = command + " )";
        db.execSQL(command);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNITION + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STAGIAIRE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROUPE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADRESSE + ";");
        onCreate(db);
    }
}
