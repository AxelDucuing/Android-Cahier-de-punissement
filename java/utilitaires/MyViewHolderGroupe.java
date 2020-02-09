package com.oim.punissement.utilitaires;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oim.punissement.R;
import com.oim.punissement.beans.GroupeObject;
import com.squareup.picasso.Picasso;

public class MyViewHolderGroupe extends RecyclerView.ViewHolder{

    private TextView nomGroupeView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolderGroupe(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        nomGroupeView = (TextView) itemView.findViewById(R.id.nomGroupe);
        imageView = (ImageView) itemView.findViewById(R.id.imageGroup);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un CapitalObject
    public void bind(GroupeObject groupeObject){
        nomGroupeView.setText(groupeObject.getNom());

        // Ici nous utilisons Picasso binder l'image depuis une URL à notre imageView.
        Picasso.with(imageView.getContext()).load(R.drawable.groupe_image).centerCrop().fit().into(imageView);
    }
}
