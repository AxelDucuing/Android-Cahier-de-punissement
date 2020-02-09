package com.oim.punissement.utilitaires;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.oim.punissement.R;
import com.oim.punissement.beans.PunitionObject;

public class MyViewHolderPunition extends RecyclerView.ViewHolder {

    private TextView dateView;
    private TextView typeView;
    private TextView groupView;
    private TextView stagiaireView;
    private TextView adresseView;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolderPunition(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        dateView = itemView.findViewById(R.id.dateView);
        typeView = itemView.findViewById(R.id.typeView);
        groupView = itemView.findViewById(R.id.groupView);
        stagiaireView = itemView.findViewById(R.id.stagiaireView);
        adresseView = itemView.findViewById(R.id.adresseView);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un CapitalObject
    public void bind(PunitionObject punitionObject){
        dateView.setText(punitionObject.getDate());
        typeView.setText(punitionObject.getTypeObject().getNom());
        groupView.setText(punitionObject.getGroupeObject().getNom());
        adresseView.setText(punitionObject.getAdresseObject().toString());
        if (punitionObject.getStagiaireObject().getId() != 0) {
            stagiaireView.setText(punitionObject.getStagiaireObject().toString());
        }
        else {
            stagiaireView.setText("");
        }
    }

}
