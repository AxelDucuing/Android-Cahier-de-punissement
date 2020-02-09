package com.oim.punissement.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.oim.punissement.utilitaires.RecyclerTouchListener;
import com.oim.punissement.R;
import com.oim.punissement.beans.GroupeObject;

import com.oim.punissement.dao.AdresseDAO;
import com.oim.punissement.dao.GroupeDAO;
import com.oim.punissement.dao.PunitionDAO;
import com.oim.punissement.dao.StagiaireDAO;
import com.oim.punissement.dao.TypeDAO;
import com.oim.punissement.utilitaires.MyAdapterGroupe;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
/**
 * Récupère la liste des groupes
 * Récup
 */
public class AccueilActivity extends AppCompatActivity
{

//      Bloc déclaratif
    private RecyclerView recyclerView;
    private ArrayList<GroupeObject> groupeList;
    private MyAdapterGroupe myAdapterGroupe;
    private GroupeDAO groupeDao;
//      onCreate
@Override
protected void onCreate(Bundle savedInstanceState)
{
    super.onCreate(savedInstanceState);

    invalidateOptionsMenu();
    setContentView(R.layout.activity_accueil);
    groupeDao = new GroupeDAO(getApplicationContext());

    // Je crée une instance de mon Helper.
    recyclerView = findViewById(R.id.recyclerViewGroupe);
    groupeList = groupeDao.getAll();
    // Je teste le nombre d'enregistrements présents dans ma base de données.
    // Si aucun enregistrement --> j'ajoute des capitales à ma BDD
    if(groupeList.size() == 0) {
        TypeDAO typeDao = new TypeDAO(getApplicationContext());
        typeDao.fillTable(getApplicationContext());
        groupeDao.fillTable(getApplicationContext());
        AdresseDAO adresseDao = new AdresseDAO(getApplicationContext());
        adresseDao.fillTable(getApplicationContext());
        StagiaireDAO stagiaireDao = new StagiaireDAO(getApplicationContext());
        stagiaireDao.fillTable(getApplicationContext());
        PunitionDAO punitionDao = new PunitionDAO(getApplicationContext());
        punitionDao.fillTable(getApplicationContext());
        groupeList = groupeDao.getAll();
    }
    // Je définis l'adaptateur de mon RecyclerView
    // Vu que ce bloc est répété trois fois, il pourrait très bien aller dans une fonction
    myAdapterGroupe = new MyAdapterGroupe(groupeList);
    recyclerView.setAdapter(myAdapterGroupe);

    recyclerView.setLayoutManager(new LinearLayoutManager(this));


    // Ici j'ajoute un listener sur les items de mon RecyclerView (grâce à la classe RecyclerTouchListener)
    recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
        @Override
        public void onClick(View view, int position)
        {
            Intent intent = new Intent(view.getContext(), ListeStagiairesActivity.class);
            // J'y ajoute un extra.
            // Grâce à l'interface Serializable, je peux véhiculer directement mon objet Capital
            intent.putExtra("CHOIXGROUPE", groupeList.get(position));
            // Et je lance l'activité
            startActivity(intent);
        }
        @Override
        public void onLongClick(View view, int position)
        {
            // Si appui long sur un item, j'affiche ma boite de dialogue, avec en paramètre la position de mon item
            // dans la liste
            Intent intent = new Intent(view.getContext(), FormulairePunitionActivity.class);
            // J'y ajoute un extra.
            // Grâce à l'interface Serializable, je peux véhiculer directement mon objet Capital
            intent.putExtra("CHOIXGROUPE", groupeList.get(position));
            // Et je lance l'activité
            startActivity(intent);
        }
    }));
}

    // Grace à onResume, je recharge mon adaptateur lorsque l'activité retrouve le focus
    @Override
    public void onResume() {
        super.onResume();
        groupeList = groupeDao.getAll();
        myAdapterGroupe = new MyAdapterGroupe(groupeList);
        recyclerView.setAdapter(myAdapterGroupe);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        menu.findItem(R.id.menu_retour_menu).setVisible(false);
        menu.findItem(R.id.menu_punissements).setVisible(true);
        menu.findItem(R.id.menu_ajout_stagiaire).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Plusieurs options a gérer suivant l'item selectionné
        super.onOptionsItemSelected(item);
        Intent addIntent;
        switch (item.getItemId()) {
            case R.id.menu_retour_menu:
                addIntent = new Intent(this, AccueilActivity.class);
                startActivity(addIntent);
                return true;
            case R.id.menu_punissements:
                addIntent = new Intent(this, ListePunitionsActivity.class);
                startActivity(addIntent);
                return true;
            case R.id.menu_ajout_stagiaire:
                addIntent = new Intent(this, FormulaireStagiaireActivity.class);
                startActivity(addIntent);
                return true;
        }
        return false;
    }

}
