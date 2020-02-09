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

import com.oim.punissement.R;
import com.oim.punissement.beans.PunitionObject;
import com.oim.punissement.dao.PunitionDAO;
import com.oim.punissement.utilitaires.MyAdapterPunition;

import java.util.List;

public class ListePunitionsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapterPunition monAdapterPunition;
    private PunitionDAO monPunitionDAO;
    private List<PunitionObject> punitionsListe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Permet de modifier le menu pour chaque activité
        invalidateOptionsMenu();
        setContentView(R.layout.activity_liste_punitions);

        Resources res = getResources();
        recyclerView = findViewById(R.id.recyclerViewListePunitions);
        monPunitionDAO = new PunitionDAO(getApplicationContext());

        punitionsListe = monPunitionDAO.getAll();

        monAdapterPunition = new MyAdapterPunition(punitionsListe);
        recyclerView.setAdapter(monAdapterPunition);

        // stockage de l'orientation en local
        int displayMode = res.getConfiguration().orientation;

        if (displayMode == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
    }

    // Grace à onResume, je recharge mon adaptateur lorsque l'activité retrouve le focus
    @Override
    public void onResume() {
        super.onResume();

        punitionsListe = monPunitionDAO.getAll();
        monAdapterPunition = new MyAdapterPunition(punitionsListe);
        recyclerView.setAdapter(monAdapterPunition);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        invalidateOptionsMenu();
        getMenuInflater().inflate(R.menu.my_menu, menu);
        menu.findItem(R.id.menu_retour_menu).setVisible(true);
        menu.findItem(R.id.menu_punissements).setVisible(false);
        menu.findItem(R.id.menu_ajout_stagiaire).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Plusieurs options a gérer suivant l'item selectionné
        super.onOptionsItemSelected(item);
        Intent addIntent = new Intent();
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
