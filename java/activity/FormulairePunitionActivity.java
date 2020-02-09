package com.oim.punissement.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oim.punissement.R;
import com.oim.punissement.beans.AdresseObject;
import com.oim.punissement.beans.GroupeObject;
import com.oim.punissement.beans.PunitionObject;
import com.oim.punissement.beans.StagiaireObject;
import com.oim.punissement.beans.TypeObject;
import com.oim.punissement.dao.PunitionDAO;
import com.oim.punissement.dao.StagiaireDAO;
import com.oim.punissement.dao.TypeDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulairePunitionActivity extends AppCompatActivity {

    private Spinner spinner;
    private TypeDAO typeDao;
    private PunitionDAO punitionDao;
    private StagiaireObject stagiaire;
    private AdresseObject adresse;
    private GroupeObject groupe;
    private CalendarView calendarView;
    private long aujourdhui;
    private Calendar calendar;
    private int an;
    private int mois;
    private int jour;
    private EditText ETadresseNumero;
    private EditText ETadresseRue;
    private EditText ETadresseCP;
    private EditText ETadresseVille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invalidateOptionsMenu();
        setContentView(R.layout.activity_formulaire_punition);

        typeDao = new TypeDAO(getApplicationContext());
        punitionDao = new PunitionDAO(getApplicationContext());

        // Récupération des champs adresse.
        ETadresseNumero = findViewById(R.id.address_number_set);
        ETadresseRue    = findViewById(R.id.street_address_set);
        ETadresseCP     = findViewById(R.id.cp_address_set);
        ETadresseVille  = findViewById(R.id.city_address_set);

        // aujourd'hui = date du jour en milisecondes
        calendar = Calendar.getInstance();
        aujourdhui = calendar.getTimeInMillis();
        calendarView = findViewById(R.id.datePunishment);
        calendarView.setMinDate(aujourdhui);
        calendarView.setDate(aujourdhui);

        an = calendar.get(Calendar.YEAR);
        mois = calendar.get(Calendar.MONTH) + 1;
        jour = calendar.get(Calendar.DAY_OF_MONTH);

        spinner = new Spinner(this);
        spinner = findViewById(R.id.list_type_spinner);
        spinnerSelectType();

        buttonReset();

        Intent intent = getIntent();
        TextView titrePunissement = findViewById(R.id.titlePunishment);
        String punissementPour = getResources().getString(R.string.punishment_for);

        // On initialise les Objets pour éviter qu'ils soient null
        stagiaire = new StagiaireObject();
        groupe = new GroupeObject();
        // J'utilise un bloc Try/catch pour gérer le cas où je n'ai pas d'extra.
        try {
            // Je lance donc mon affectation
            if(intent.hasExtra("STAGIAIREEXTRA")){
                stagiaire = (StagiaireObject) intent.getSerializableExtra("STAGIAIREEXTRA");
                String title = punissementPour + " " + stagiaire.toString();
                titrePunissement.setText(title);
            }
            else if (intent.hasExtra("CHOIXGROUPE")){
                // On récupère le groupe via l'Intent
                groupe = (GroupeObject) intent.getSerializableExtra("CHOIXGROUPE");
                String title = punissementPour + " " + groupe.getNom();
                titrePunissement.setText(title);
            }
        } catch(NullPointerException error){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.extra_erreur), Toast.LENGTH_LONG).show();
            finish();
        }

        // RÉGLAGE DU LISTENER SUR LE CALENDRIER;
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                    an = year;
                    mois = month + 1;
                    jour = dayOfMonth;
            }
        });
    }

    private void buttonReset() {
        Button reset = findViewById(R.id.resetButton);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aujourdhui = calendar.getTimeInMillis();
                calendarView.setDate(aujourdhui);
            }
        });
    }

    private void spinnerSelectType() {
        // Création d'une liste d'éléments à mettre dans le Spinner et récupération des données de la DataBase via l'objet TypeDAO
        ArrayList<TypeObject> ArrayListType = typeDao.getAll();

        /*Le spinner à besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
         * un fichier de présentation par défaut (android.R.layout.simple_spinner_item)
         * avec la liste des elements */
        ArrayAdapter<TypeObject> myArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, ArrayListType);

        // On définit la présentation du spinner quand il est déroulé
        myArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Enfin on passe l'adapter au Spinner
        spinner.setAdapter(myArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_type_0), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_type_1), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.select_type_2), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        getMenuInflater().inflate(R.menu.my_menu, menu);
        menu.findItem(R.id.menu_retour_menu).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Plusieurs options a gérer suivant l'item selectionné
        super.onOptionsItemSelected(item);
        Intent addIntent;
        switch (item.getItemId()){
            case R.id.menu_retour_menu:
                addIntent = new Intent(this, AccueilActivity.class);
                startActivity(addIntent);
                finish();
                return true;
            case R.id.menu_punissements:
                addIntent = new Intent(this, ListePunitionsActivity.class);
                startActivity(addIntent);
                finish();
                return true;
            case R.id.menu_ajout_stagiaire:
                addIntent = new Intent(this, FormulaireStagiaireActivity.class);
                startActivity(addIntent);
                finish();
                return true;
        }

        return false;
    }

    /**
     * Vérifie et ajoute l'adresse. Renvoie true si c'est bon
     *
     * @return true si l'adresse a été validée et ajoutée
     */
    public boolean addAddress(){
        String adresseNumero ;
        String adresseRue;
        String adresseCP;
        String adresseVille;
        boolean checked = false;

        adresseNumero = ETadresseNumero.getText().toString().trim();
        adresseRue    = ETadresseRue.getText().toString().trim();
        adresseCP     = ETadresseCP.getText().toString().trim();
        adresseVille  = ETadresseVille.getText().toString().trim();

        // Vérification du numéro de rue et du CP contiennent bien que des nombres et que la taille de CP soit bien inférieur ou égale à 5
        if(!adresseNumero.matches("[0-9]+") || !adresseCP.matches("[0-9]{5}")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_address_numbers), Toast.LENGTH_LONG).show();
            return false;
        }
        int numeroDeRue = Integer.parseInt(adresseNumero);

        // Morceau par Loic
        // Vérification de la rue
        String [] listeVoie = {"rue", "place", "chemin", "allée", "avenue", "impasse", "carrefour", "hall", "boulevard", "voie" };
        Pattern pattern;
        Matcher matcher;

        // On vérifie si dans la chaine de caractère adresseName contient un des mots cité dans listeVoie
        for(String voie : listeVoie) {
            pattern = Pattern.compile(voie);
            matcher = pattern.matcher(adresseRue.toLowerCase());
            if (matcher.find()){
                checked = true;
                break;
            }
        }
        // Si le nom d'une voie n'a pas été trouvée
        if (!checked) {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_adress_pathway), Toast.LENGTH_LONG).show();
        }

        if(adresseRue.matches("[^0-9]*[0-9]+[^0-9]*")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_address_street), Toast.LENGTH_LONG).show();
            return false ;
        }
        if(adresseVille.matches("[^0-9]*[0-9]+[^0-9]*")){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_address_street), Toast.LENGTH_LONG).show();
            return false ;
        }

        adresse = new AdresseObject(numeroDeRue, adresseRue, adresseVille, adresseCP );
        return true ;
    }


    /**
     * Méthode qui permet d'ajouter un punissement avec les informations saisis au stagiaire séléctionné
     * @param view : C'est la vue utilisé
     */
    public void addPunishmentClicked(View view) {
        // On récupère le spinneur pour le type de punition
        String type = spinner.getSelectedItem().toString();

        String sMois;
        String sjour;
        // On vérifie le format et rajoute "0" devant si jamais
        if (mois <10){
            sMois = "0"+mois;
        }
        else
        {
            sMois = String.valueOf(mois) ;
        }

        if (jour <10){
            sjour = "0"+jour;
        }
        else{
            sjour = String.valueOf(jour);
        }

        // Date créé au format choisie
        String sDate = an+"-"+sMois+"-"+sjour;

        // On vérifie si les champs on était remplis
        if(!type.isEmpty() && addAddress()){
            // On créé l'email qui sera envoyé
            Intent emailIntent = new Intent();
            emailIntent.setAction(Intent.ACTION_SEND);
            emailIntent.setType("message/rfc822");

            // Tableau des adresses mail à qui l'envoyer
            String[] liste_email;
            PunitionObject punition;
            // On vérifie si la liste est vide
            if(groupe.getId() == 0) {
                punition = new PunitionObject(sDate, stagiaire.getGroupeObject(), stagiaire, adresse, new TypeObject(type));
                liste_email = new String[1];
                liste_email[0] = punition.getStagiaireObject().getMail();
            } else {
                punition = new PunitionObject(sDate, groupe, stagiaire, adresse, new TypeObject(type));
                StagiaireDAO stagiaireDAO = new StagiaireDAO(getApplicationContext());
                List<StagiaireObject> stagiairesListe = stagiaireDAO.getAllByGroupe(groupe.getId());
                liste_email = new String[stagiairesListe.size()];
                int compteur = 0;
                for(StagiaireObject stagiaire : stagiairesListe) {
                    liste_email[compteur] = stagiaire.getMail();
                    compteur++;
                }
            }
            punitionDao.insert(punition);

            emailIntent.putExtra(Intent.EXTRA_EMAIL, liste_email);
            emailIntent.putExtra(Intent.EXTRA_CC, new String[] { });
            emailIntent.putExtra(Intent.EXTRA_BCC, new String[] { });
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Nouvelle punition");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Vous êtes l'heureux gagnant d'une punition de type : " + type + "\n" +
                    "Cette chance est due grâce à votre comportement ou votre vocabulaire, SURTOUT ne changé rien nous aimons ça !\n" +
                    "\nRendez vous donc le " + sDate + " pour la dégustation de votre punition.\n" +
                    "A vos marques, prêts, CUISINEZ !!!");
            try {
                startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                finish();
                Log.i("Finished sending email...", "");
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_punishment_record), Toast.LENGTH_LONG).show();
        }
    }
}
