package com.oim.punissement.utilitaires;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.oim.punissement.R;
import com.oim.punissement.beans.GroupeObject;

import java.util.List;

public class MyAdapterGroupe extends RecyclerView.Adapter<MyViewHolderGroupe> {

    private List<GroupeObject> maListeGroupe;

    //ajouter un constructeur prenant en entrée une liste
    public MyAdapterGroupe(List<GroupeObject> list) {
        this.maListeGroupe = list;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolderGroupe onCreateViewHolder( ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_card_groupe,viewGroup,false);
        return new MyViewHolderGroupe(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder( MyViewHolderGroupe myViewHolder, int position) {
        GroupeObject groupeObject = maListeGroupe.get(position);
        myViewHolder.bind(groupeObject);
    }

    @Override
    public int getItemCount() {
        return maListeGroupe.size();
    }
}