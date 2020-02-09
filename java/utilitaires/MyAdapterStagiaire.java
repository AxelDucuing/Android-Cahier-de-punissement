package com.oim.punissement.utilitaires;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oim.punissement.R;
import com.oim.punissement.beans.StagiaireObject;

import java.util.List;

public class MyAdapterStagiaire extends RecyclerView.Adapter<MyViewHolderStagiaire> {

    private List<StagiaireObject> maListeStagiaires;

    //ajouter un constructeur prenant en entrée une liste
    public MyAdapterStagiaire(List<StagiaireObject> maListeStagiaires) {
        this.maListeStagiaires = maListeStagiaires;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolderStagiaire onCreateViewHolder( ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_card_stagiaire,viewGroup,false);
        return new MyViewHolderStagiaire(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder( MyViewHolderStagiaire myViewHolder, int position) {
        StagiaireObject stagiaireObject = maListeStagiaires.get(position);
        myViewHolder.bind(stagiaireObject);
    }

    @Override
    public int getItemCount() {
        return maListeStagiaires.size();
    }
}