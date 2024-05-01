package entities;

import java.util.ArrayList;
import java.util.List;

public class traitement {
    private int id;
    private String nom;
    private int duree;
    private String posologie;
    private String notes;
    private int cout;
    private List<visite> visites;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getPosologie() {
        return posologie;
    }

    public void setPosologie(String posologie) {
        this.posologie = posologie;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getCout() {
        return cout;
    }

    public void setCout(int cout) {
        this.cout = cout;
    }


    public traitement(int id, String nom, int duree, String posologie, String notes, int cout) {
        this.id = id;
        this.nom = nom;
        this.duree = duree;
        this.posologie = posologie;
        this.notes = notes;
        this.cout = cout;
    }

    public traitement() {
    }

    public traitement(String nom, int duree, String posologie, String notes, int cout) {
        this.nom = nom;
        this.duree = duree;
        this.posologie = posologie;
        this.notes = notes;
        this.cout = cout;
    }

    @Override
    public String toString() {
        /*
        List<String> attributes = new ArrayList<>();
        if (id > 0) attributes.add("id=" + id);
        if (nom != null && !nom.isEmpty()) attributes.add("nom='" + nom + "'");
        if (duree > 0) attributes.add("duree=" + duree);
        if (posologie != null && !posologie.isEmpty()) attributes.add("posologie='" + posologie + "'");
        if (notes != null && !notes.isEmpty()) attributes.add("notes='" + notes + "'");
        if (cout > 0) attributes.add("cout=" + cout);

        return "traitement{" + String.join(", ", attributes) + "}";
    }*/
        return "traitement{" +
                (id > 0 ? "id=" + id : "") +
                (nom != null && !nom.isEmpty() ? ", nom='" + nom + "'" : "") +
                (duree > 0 ? ", duree=" + duree : "") +
                (posologie != null && !posologie.isEmpty() ? ", posologie='" + posologie + "'" : "") +
                (notes != null && !notes.isEmpty() ? ", notes='" + notes + "'" : "") +
                (cout > 0 ? ", cout=" + cout : "") +
                '}';}
}