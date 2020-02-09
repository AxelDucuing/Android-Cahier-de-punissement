package com.oim.punissement.utilitaires;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.oim.punissement.R;
import com.oim.punissement.beans.PunitionObject;

import java.util.List;

public class MyAdapterPunition extends RecyclerView.Adapter<MyViewHolderPunition> {

    private List<PunitionObject> maListePunition;

    //ajouter un constructeur prenant en entrée une liste
    public MyAdapterPunition(List<PunitionObject> maListePunition) {
        this.maListePunition = maListePunition;
    }

    //cette fonction permet de créer les viewHolder
    //et par la même indiquer la vue à inflater (à partir des layout xml)
    @Override
    public MyViewHolderPunition onCreateViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_card_punition,viewGroup,false);
        return new MyViewHolderPunition(view);
    }

    //c'est ici que nous allons remplir notre cellule avec le texte/image de chaque MyObjects
    @Override
    public void onBindViewHolder(MyViewHolderPunition myViewHolder, int position) {
        PunitionObject punitionObject = maListePunition.get(position);
        myViewHolder.bind(punitionObject);
    }

    @Override
    public int getItemCount() {
        return maListePunition.size();
    }

}
