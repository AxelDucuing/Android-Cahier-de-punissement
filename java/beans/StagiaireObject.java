package com.oim.punissement.beans;


import java.io.Serializable;

public class StagiaireObject implements Serializable
{

    /* _______________ VARIABLES _______________*/

    private int id;
    private String nom;
    private String prenom;
    private String url;
    private String mail;
    private  GroupeObject groupeObject;
    private AdresseObject adresseObject;



    /* _______________ CONSTRUCTEURS _______________*/

    public StagiaireObject(){}

    public StagiaireObject(int id, String nom, String prenom, String url, String mail, GroupeObject groupeObject, AdresseObject adresseObject)
    {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.url = url;
        this.mail = mail;
        this.groupeObject = groupeObject;
        this.adresseObject = adresseObject;
    }

    public StagiaireObject(String nom, String prenom, String url, String mail, GroupeObject groupeObject, AdresseObject adresseObject)
    {
        this.nom = nom;
        this.prenom = prenom;
        this.url = url;
        this.mail = mail;
        this.groupeObject = groupeObject;
        this.adresseObject = adresseObject;
    }



    /* _______________ GETTERS ANS SETTERS _______________*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getPrenom()
    {
        return prenom;
    }

    public void setPrenom(String prenom)
    {
        this.prenom = prenom;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public GroupeObject getGroupeObject()
    {
        return groupeObject;
    }

    public void setGroupeObject(GroupeObject groupeObject)
    {
        this.groupeObject = groupeObject;
    }

    public AdresseObject getAdresseObject()
    {
        return adresseObject;
    }

    public void setAdresseObject(AdresseObject adresseObject)
    {
        this.adresseObject = adresseObject;
    }

    @Override
    public String toString(){
        return prenom +" "+ nom;
    }

}
