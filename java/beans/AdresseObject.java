package com.oim.punissement.beans;

import java.io.Serializable;

public class AdresseObject implements Serializable
{

    /* _______________ VARIABLES _______________*/

    private int id;
    private int numero_rue;
    private String nom_rue;
    private String cp;
    private String ville;



    /* _______________ CONSTRUCTEURS _______________*/

    public AdresseObject(){}

    public AdresseObject(int id, int numero_rue, String nom_rue, String ville, String cp)
    {
        this.id = id;
        this.numero_rue = numero_rue;
        this.nom_rue = nom_rue;
        this.cp = cp;
        this.ville = ville;
    }

    public AdresseObject( int numero_rue, String nom_rue, String ville, String cp)
    {
        this.numero_rue = numero_rue;
        this.nom_rue = nom_rue;
        this.cp = cp;
        this.ville = ville;
    }



    /* _______________ GETTERS AND SETTERS _______________*/

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getNumero_rue()
    {
        return numero_rue;
    }

    public void setNumero_rue(int numero_rue)
    {
        this.numero_rue = numero_rue;
    }

    public String getNom_rue()
    {
        return nom_rue;
    }

    public void setNom_rue(String nom_rue)
    {
        this.nom_rue = nom_rue;
    }

    public String getCp()
    {
        return cp;
    }

    public void setCp(String cp)
    {
        this.cp = cp;
    }

    public String getVille()
    {
        return ville;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    @Override
    public String toString(){
        return numero_rue + " " + nom_rue + " " + ", " + cp + ", " + ville ;
    }

}
