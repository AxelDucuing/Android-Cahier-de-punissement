package com.oim.punissement.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.oim.punissement.utilitaires.RecyclerTouchListener;
import com.oim.punissement.R;
import com.oim.punissement.beans.GroupeObject;
import com.oim.punissement.beans.StagiaireObject;
import com.oim.punissement.dao.StagiaireDAO;
import com.oim.punissement.utilitaires.MyAdapterStagiaire;

import java.util.List;

public class ListeStagiairesActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private List<StagiaireObject> stagiairesListe;
    private MyAdapterStagiaire monAdapterStagiaire;
    private GroupeObject monGroupe ;
    private StagiaireDAO monStagiaireDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_stagiaires);
        Resources res = getResources();
        monStagiaireDAO = new StagiaireDAO(getApplicationContext());
        recyclerView = findViewById(R.id.recyclerViewListeStagiaires);

        // stockage de l'orientation en local
        int displayMode = res.getConfiguration().orientation;

        Intent intent = getIntent();

        try {
            // Je lance donc mon affectation
            monGroupe = (GroupeObject) intent.getSerializableExtra("CHOIXGROUPE");

            stagiairesListe = monStagiaireDAO.getAllByGroupe(monGroupe.getId());

            monAdapterStagiaire = new MyAdapterStagiaire(stagiairesListe);
            recyclerView.setAdapter(monAdapterStagiaire);

        } catch(NullPointerException error){
            // --> L'affectation a généré une erreur NullPointerException
            monGroupe = new GroupeObject();
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_list_no_interns), Toast.LENGTH_LONG).show();
            finish();
        }

        if(monGroupe.getNom().isEmpty()){
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.error_list_no_interns), Toast.LENGTH_LONG).show();
            finish();
        }

        if (displayMode == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }

        // Ici j'ajoute un listener sur les items de mon RecyclerView (grâce à la classe RecyclerTouchListener)
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), FicheStagiaireActivity.class);
                // J'y ajoute un extra.
                // Grâce à l'interface Serializable, je peux véhiculer directement mon objet Stagiaire
                intent.putExtra("STAGIAIREEXTRA", stagiairesListe.get(position));
                // Et je lance l'activité
                startActivity(intent);
            }
            @Override
            public void onLongClick(View view, int position) {
                // Si appui long sur un item, j'affiche FormulairePunitionActivity,
                // avec en paramètre la position de mon item dans la liste
                Intent intent = new Intent(view.getContext(), FormulairePunitionActivity.class);
                // J'y ajoute un extra.
                // Grâce à l'interface Serializable, je peux véhiculer directement mon objet Stagiaire
                intent.putExtra("STAGIAIREEXTRA", stagiairesListe.get(position));
                // Et je lance l'activité
                startActivity(intent);
            }
        }));
    }

    // Grace à onResume, je recharge mon adaptateur lorsque l'activité retrouve le focus
    @Override
    public void onResume() {
        super.onResume();

        stagiairesListe = monStagiaireDAO.getAllByGroupe(monGroupe.getId());
        monAdapterStagiaire = new MyAdapterStagiaire(stagiairesListe);
        recyclerView.setAdapter(monAdapterStagiaire);
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
}
