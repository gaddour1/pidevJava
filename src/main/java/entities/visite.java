package entities;

import java.util.Date;

public class visite {
    private int id;
    private String date;
    private String lieu;
    private String heure;
    private  traitement trait;
    public traitement getTraitement() {
        return trait;
    }

    public void setTraitement(traitement trait) {
        this.trait= trait;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public visite(int id, String date, String lieu, String heure, traitement trait) {
        this.id = id;
        this.date = date;
        this.lieu = lieu;
        this.heure = heure;
        this.trait= trait;
    }

    public visite() {
    }

    public visite(String date, String lieu, String heure, traitement trait) {
        this.date = date;
        this.lieu = lieu;
        this.heure = heure;
        this.trait= trait;

    }

    @Override
    public String toString() {
        String traitementDetails = this.trait.getId() > 0 ? ", traitementID=" + this.trait.getId() : "";
        return "visite{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", lieu='" + lieu + '\'' +
                ", heure='" + heure + '\'' +
                traitementDetails +
                '}';
}}

