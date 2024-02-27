package com.epf.rentmanager.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Client {
    private long id;
    private String name;
    private String prenom;
    private String email;
    private LocalDate naissance;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd 00:00:00");

    public Client(long id, String name, String prenom, String email, String naissance) {
        this.id = id;
        this.name = name;
        this.prenom = prenom;
        this.email = email;
        this.naissance = LocalDate.parse(naissance,formatter);
    }

    public Client() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getNaissance() {
        return naissance;
    }

    public void setNaissance(LocalDate naissance) {
        this.naissance = naissance;
    }

    @Override
    public String toString() {
        return "ReservationModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", naissance=" + naissance +
                '}';
    }
}
