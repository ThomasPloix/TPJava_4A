package com.epf.rentmanager.model;

public class Vehicle {

    private long id;
    private String constructeur;
    private int seats;
    private String modele;

    public Vehicle(long id , String constructeur, int nb_places,String modele) {
        this.id=id;
        this.constructeur = constructeur;
        this.seats = nb_places;
        this.modele = modele;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Vehicle() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getConstructeur() {
        return constructeur;
    }

    public void setConstructeur(String constructeur) {
        this.constructeur = constructeur;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int nb_places) {
        this.seats = nb_places;
    }

    @Override
    public String toString() {
        return "VehicleModel{" +
                "id=" + id +
                ", constructeur='" + constructeur + '\'' +
                ", nb_places=" + seats +
                '}';
    }
}
