package com.oim.punissement.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.oim.punissement.R;
import com.oim.punissement.beans.AdresseObject;
import com.oim.punissement.beans.GroupeObject;
import com.oim.punissement.beans.StagiaireObject;
import com.oim.punissement.dao.GroupeDAO;
import com.oim.punissement.dao.StagiaireDAO;
import com.oim.punissement.utilitaires.FormStagiaireCheckup;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaireStagiaireActivity extends AppCompatActivity {

    private GroupeDAO       groupeDao;
    private StagiaireDAO    stagiaireDao;
    private Spinner         spinner;
    private EditText        eNom;
    private EditText        ePrenom;
    private EditText        eUrl;
    private EditText        eEmail;
    private EditText        eAdresseNum;
    private EditText        eAdresseName;
    private EditText        eAdresseCP;
    private EditText        eAdresseCity;
    private int             id_stagiaire_modif;
    private GroupeObject    eGroupeObject;
    private StagiaireObject monStagiaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invalidateOptionsMenu();
        groupeDao = new GroupeDAO(this);
        stagiaireDao = new StagiaireDAO(this);
        setContentView(R.layout.activity_formulaire_stagiaire);

        TextView titleTrainee = findViewById(R.id.titleTrainee);

        id_stagiaire_modif = 0;
        eNom = findViewById(R.id.nameTrainee);
        ePrenom = findViewById(R.id.fornameTrainee);
        eUrl = findViewById(R.id.imageURLTrainee);
        eEmail = findViewById(R.id.emailTrainee);
        eAdresseNum = findViewById(R.id.adresseNumTrainee);
        eAdresseName = findViewById(R.id.adresseNameTrainee);
        eAdresseCP = findViewById(R.id.adresseCPTrainee);
        eAdresseCity = findViewById(R.id.adresseCityTrainee);

        //Récupération du Spinner déclaré dans le layout activity_formulaire_punition
        spinner = new Spinner(this);
        spinner = findViewById(R.id.spinGroupeTrainee);

        buttonReset();

        // On vérifie si la page qui doit être afficher est la page d'ajout ou de modification

        Intent intent = getIntent();
        if(intent.hasExtra("STAGIAIREEDIT")) {
            titleTrainee.setText(R.string.modify_intern);
            monStagiaire = (StagiaireObject) intent.getSerializableExtra("STAGIAIREEDIT");
            id_stagiaire_modif = monStagiaire.getId();
            eNom.setText(monStagiaire.getNom());
            ePrenom.setText(monStagiaire.getPrenom());
            eEmail.setText(monStagiaire.getMail());
            eUrl.setText(monStagiaire.getUrl());
            eAdresseNum.setText(String.valueOf(monStagiaire.getAdresseObject().getNumero_rue()));
            eAdresseName.setText(monStagiaire.getAdresseObject().getNom_rue());
            eAdresseCP.setText(monStagiaire.getAdresseObject().getCp());
            eAdresseCity.setText(monStagiaire.getAdresseObject().getVille());
            eGroupeObject = monStagiaire.getGroupeObject();
        } else {
            eUrl.setText(getResources().getString(R.string.image_stagiaire));
            titleTrainee.setText(R.string.menu_add_intern);
        }

        spinnerSelectType();
    }

    private void buttonReset() {
        Button reset = findViewById(R.id.resetButton);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eNom.setText("");
                eNom.setHint(getString(R.string.hint_name));
                ePrenom.setText("");
                ePrenom.setHint(getString(R.string.hint_forename));
                eUrl.setText("");
                eUrl.setHint(getString(R.string.hint_url));
                eEmail.setText("");
                eEmail.setHint(getString(R.string.hint_mail));
                eAdresseNum.setText("");
                eAdresseNum.setHint(getString(R.string.hint_address_num));
                eAdresseName.setText("");
                eAdresseName.setHint(getString(R.string.hint_address_name));
                eAdresseCP.setText("");
                eAdresseCP.setHint(getString(R.string.hint_address_cp));
                eAdresseCity.setText("");
                eAdresseCity.setHint(getString(R.string.hint_address_city));
            }
        });
    }

    private void spinnerSelectType() {
        ArrayList<GroupeObject> arrayListType = groupeDao.getAll();

        /*Le spinner à besoin d'un adapter pour sa presentation alors on lui passe le context(this) et
         * un fichier de présentation par défaut (android.R.layout.simple_spinner_item)
         * avec la liste des elements */
        ArrayAdapter<GroupeObject> myArrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayListType);
        //on vérifie le groupe de l'utilisateur.

        // On définit la présentation du spinner quand il est déroulé
        myArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Enfin on passe l'adapter au Spinner
        
        spinner.setAdapter(myArrayAdapter);

        Intent intent = getIntent();
        if(intent.hasExtra("STAGIAIREEDIT")) {

            String eGroupeNom = eGroupeObject.getNom();
            int index = spinner.getCount();

            for (int i=0;i<arrayListType.size();i++){
                if (spinner.getItemAtPosition(i).toString().equals(eGroupeNom)){
                    index = i;
                    break;
                }
            }
            spinner.setSelection(index, true);
        }
        else{
            spinner.setSelection(0, true);
        }

    }

    public void TraineeClicked(View view) {
        FormStagiaireCheckup form = new FormStagiaireCheckup();
        // On récupère les données saisies
        String nom     = eNom.getText().toString().trim();
        String prenom  = ePrenom.getText().toString().trim();
        String url     = eUrl.getText().toString().trim();
        String email   = eEmail.getText().toString().trim();
        String adresseNum = eAdresseNum.getText().toString();
        String adresseName = eAdresseName.getText().toString().trim();
        String adresseCP = eAdresseCP.getText().toString().trim();
        String adresseCity = eAdresseCity.getText().toString().trim();
        String groupe  = spinner.getSelectedItem().toString();
        // Contiendra les erreur du formulaire (s'il y en a)
        String Erreur = "";

        // On fait les vérification de tous les champs
        Erreur = form.CheckForm(nom, prenom, url, email, adresseNum, adresseName, adresseCP, adresseCity);
        // Si l'URL est vide, on ajoute l'image par default
        if (url.isEmpty()) {
            url = getResources().getString(R.string.image_stagiaire);
        }

        // On supprime les espace innutile
        Erreur = Erreur.trim();

        // On vérifie si des erreurs ont étaient trouvées
        if (!Erreur.equals("")){
            Toast.makeText(getApplicationContext(), Erreur, Toast.LENGTH_LONG).show();
        } else {
            // Si ok on convertie eAdresseNum (String) en int et on instancie l'adresse
            int adresseNumInt = Integer.parseInt(eAdresseNum.getText().toString());
            AdresseObject adresse = new AdresseObject(adresseNumInt, adresseName, adresseCity, adresseCP);
            StagiaireObject stagiaire = new StagiaireObject(nom, prenom, url, email, new GroupeObject(groupe), adresse);

            stagiaire.setId(id_stagiaire_modif);
            /* On vérifie :
                - Si id = 0 alors on doit ajouter ce nouveau stagiaire
                - Sinon on doit mettre à jour le stagiaire
             */
            if (stagiaire.getId() == 0) {
                stagiaireDao.insert(stagiaire);
            } else {
                stagiaireDao.update(stagiaire);
            }
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        getMenuInflater().inflate(R.menu.my_menu, menu);
        menu.findItem(R.id.menu_retour_menu).setVisible(true);
        menu.findItem(R.id.menu_punissements).setVisible(true);
        menu.findItem(R.id.menu_ajout_stagiaire).setVisible(false);
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
}
