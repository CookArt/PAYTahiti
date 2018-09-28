package com.cookart.paytahiti;

public class Parking{
    public String nom;
    public double latitude, longitude;
    public long nb_places_restantes;

    public Parking() {
    }

    public Parking(String nom, double latitude, double longitude, long nb_places_restantes) {
        this.nom = nom;
        this.latitude = latitude;
        this.longitude = longitude;
        this.nb_places_restantes = nb_places_restantes;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getNb_places_restantes() {
        return nb_places_restantes;
    }

    public void setNb_places_restantes(long nb_places_restantes) {
        this.nb_places_restantes = nb_places_restantes;
    }
}
