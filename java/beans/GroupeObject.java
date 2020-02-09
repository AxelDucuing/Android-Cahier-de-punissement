package com.oim.punissement.beans;


import java.io.Serializable;

public class GroupeObject implements Serializable
{

    /* _______________ VARIABLES _______________*/

    private int id;
    private String nom;



    /* _______________ CONSTRUCTEURS _______________*/

    public GroupeObject(){}

    public GroupeObject(int id, String nom)
    {
        this.id = id;
        this.nom = nom;
    }

    public GroupeObject(String nom)
    {
        this.nom = nom;
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

    public String getNom()
    {
        return nom;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }


    @Override
    public String toString(){
        return nom;
    }
}