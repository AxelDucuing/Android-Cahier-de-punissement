package com.oim.punissement.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.oim.punissement.R;
import com.oim.punissement.beans.StagiaireObject;
import com.oim.punissement.dao.StagiaireDAO;
import com.squareup.picasso.Picasso;


public class FicheStagiaireActivity extends AppCompatActivity {

    // Bloc de déclaration
    private TextView  etName;
    private TextView  etUsername;
    private TextView  etMail;
    private TextView  etAdresseNum;
    private TextView  etAdresseName;
    private TextView  etAdresseCP;
    private TextView  etAdresseCity;
    private TextView  etGroupe;
    private ImageView etImageUrl;
    private StagiaireObject monStagiaire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        invalidateOptionsMenu();
        setContentView(R.layout.activity_fiche_stagiaire);

        etName          = findViewById(R.id.name);
        etUsername      = findViewById(R.id.username);
        etMail          = findViewById(R.id.email);
        etAdresseNum    = findViewById(R.id.adresse_num);
        etAdresseName   = findViewById(R.id.adresse_name);
        etAdresseCP     = findViewById(R.id.adresse_cp);
        etAdresseCity   = findViewById(R.id.adresse_city);
        etGroupe        = findViewById(R.id.groupe);
        etImageUrl      = findViewById(R.id.imageURL);

        Intent intent = getIntent();
        if(intent.hasExtra("STAGIAIREEXTRA")) {
            monStagiaire = (StagiaireObject) intent.getSerializableExtra("STAGIAIREEXTRA");
            displayStagiaire(monStagiaire);
        }

    }

    private void displayStagiaire(StagiaireObject monStagiaire) {

        Picasso.with(etImageUrl.getContext()).load(monStagiaire.getUrl()).centerCrop().fit().into(etImageUrl);
        etName.setText(monStagiaire.getNom());
        etUsername.setText(monStagiaire.getPrenom());
        etMail.setText(monStagiaire.getMail());
        etAdresseNum.setText(String.valueOf(monStagiaire.getAdresseObject().getNumero_rue()));
        etAdresseName.setText(monStagiaire.getAdresseObject().getNom_rue());
        etAdresseCP.setText(monStagiaire.getAdresseObject().getCp());
        etAdresseCity.setText(monStagiaire.getAdresseObject().getVille());
        etGroupe.setText((monStagiaire.getGroupeObject().getNom()));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        getMenuInflater().inflate(R.menu.my_menu, menu);
        menu.findItem(R.id.menu_retour_menu).setVisible(true);
        menu.findItem(R.id.menu_punissements).setVisible(true);
        menu.findItem(R.id.menu_ajout_stagiaire).setVisible(true);
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

    @Override
    public void onResume(){
        super.onResume();
        StagiaireDAO stagiaireDao = new StagiaireDAO(this);
        monStagiaire = stagiaireDao.getById(monStagiaire.getId());
        displayStagiaire(monStagiaire);
    }

    public void editButtonClicked(View view){
        Intent editIntent = new Intent(this, FormulaireStagiaireActivity.class);
        editIntent.putExtra("STAGIAIREEDIT", monStagiaire);
        startActivity(editIntent);
    }
}
