package entities;

import java.util.Objects;

public class event {
    private int id;
    private String titre;
    private String description;

    private String lieu; // New attribute for location
    private String date; // New attribute for date

    private String image;

    public event(int id, String titre, String description, String date, String lieu,String image) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.date = date;
        this.image = image;
    }

    public event(String titre, String description, String date, String lieu,String image) {
        this.titre = titre;
        this.description = description;
        this.lieu = lieu;
        this.date = date;
        this.image = image;
    }

    public event() {
    }

    public event(event selectedItem) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        event event = (event) o;
        return id == event.id && Objects.equals(titre, event.titre) && Objects.equals(description, event.description) && Objects.equals(lieu, event.lieu) && Objects.equals(date, event.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, titre, description, lieu, date);
    }

    @Override
    public String toString() {
        return "event{" +
                "id=" + id +
                ", titre='" + titre + '\'' +
                ", description='" + description + '\'' +
                ", lieu='" + lieu + '\'' +
                ", date='" + date + '\'' +
                ", image='" + image + '\'' +
                '}';

    }
}


