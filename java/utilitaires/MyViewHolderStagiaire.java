package com.oim.punissement.utilitaires;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.oim.punissement.R;
import com.oim.punissement.beans.StagiaireObject;
import com.squareup.picasso.Picasso;

public class MyViewHolderStagiaire extends RecyclerView.ViewHolder{

    private TextView prenomView;
    private TextView nomView;
    private TextView emailView;
    private ImageView imageView;

    //itemView est la vue correspondante à 1 cellule
    public MyViewHolderStagiaire(View itemView) {
        super(itemView);

        //c'est ici que l'on fait nos findView
        prenomView = itemView.findViewById(R.id.prenomCard);
        nomView = itemView.findViewById(R.id.nomCard);
        emailView = itemView.findViewById(R.id.emailCard);
        imageView = itemView.findViewById(R.id.imageCard);
    }

    //puis ajouter une fonction pour remplir la cellule en fonction d'un CapitalObject
    public void bind(StagiaireObject stagiaireObject){
        prenomView.setText(stagiaireObject.getPrenom());
        nomView.setText(stagiaireObject.getNom());
        emailView.setText(stagiaireObject.getMail());

        // Ici nous utilisons Picasso binder l'image depuis une URL à notre imageView.
        Picasso.with(imageView.getContext()).load(stagiaireObject.getUrl()).centerCrop().fit().into(imageView);
    }
}
