package com.oim.punissement.beans;

import java.io.Serializable;

/*      bean Punition   */
public class PunitionObject implements Serializable
{

    /* _______________ VARIABLES _______________*/

    private int id;
    private String date;
    private GroupeObject groupeObject;
    private StagiaireObject stagiaireObject;
    private AdresseObject adresseObject;
    private TypeObject typeObject;


    /* _______________ CONSTRUCTEURS _______________*/

    public PunitionObject(){}

    public PunitionObject(int id, String date, GroupeObject groupeObject,
      StagiaireObject stagiaireObject, AdresseObject adresseObject, TypeObject typeObject)
    {
        this.id = id;
        this.date = date;
        this.groupeObject = groupeObject;
        this.stagiaireObject = stagiaireObject;
        this.adresseObject = adresseObject;
        this.typeObject = typeObject;
    }

    public PunitionObject(String date, GroupeObject groupeObject,
                          StagiaireObject stagiaireObject, AdresseObject adresseObject, TypeObject typeObject)
    {
        this.date = date;
        this.groupeObject = groupeObject;
        this.stagiaireObject = stagiaireObject;
        this.adresseObject = adresseObject;
        this.typeObject = typeObject;
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

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public GroupeObject getGroupeObject()
    {
        return groupeObject;
    }

    public void setGroupeObject(GroupeObject groupeObject)
    {
        this.groupeObject = groupeObject;
    }

    public StagiaireObject getStagiaireObject()
    {
        return stagiaireObject;
    }

    public void setStagiaireObject(StagiaireObject stagiaireObject)
    {
        this.stagiaireObject = stagiaireObject;
    }

    public AdresseObject getAdresseObject()
    {
        return adresseObject;
    }

    public void setAdresseObject(AdresseObject adresseObject)
    {
        this.adresseObject = adresseObject;
    }

    public TypeObject getTypeObject()
    {
        return typeObject;
    }

    public void setTypeObject(TypeObject typeObject)
    {
        this.typeObject = typeObject;
    }
}
