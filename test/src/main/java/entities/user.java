package entities;

import java.util.Objects;

public class user {
    private int id;
    private String nom;
    private String username;

    private int numero; // New attribute for location
    private int cin; // New attribute for date

    private String adresse;
    private String role;

    private String email;

    private String password;



    public user(int id, String nom, String username, int numero, int cin, String adresse, String role, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.username = username;
        this.numero = numero;
        this.cin = cin;
        this.adresse = adresse;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public user(String nom, String username, int numero, int cin, String adresse, String role, String email, String password) {
        this.nom = nom;
        this.username = username;
        this.numero = numero;
        this.cin = cin;
        this.adresse = adresse;
        this.role = role;
        this.email = email;
        this.password = password;
    }

    public user() {
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getRole() { return role; }

    public void setRole(String role) {this.role = role;}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        user user = (user) o;
        return id == user.id && numero == user.numero && cin == user.cin && Objects.equals(nom, user.nom) && Objects.equals(username, user.username) && Objects.equals(adresse, user.adresse) && Objects.equals(email, user.email) && Objects.equals(password, user.password);
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", username='" + username + '\'' +
                ", numero=" + numero +
                ", cin=" + cin +
                ", adresse='" + adresse + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, username, numero, cin, adresse,role, email, password);
    }

    public boolean isValidNom(String nom) {
        return nom != null && nom.length() <= 15;
    }
    public boolean isValidUsername(String username) {
        return username != null && username.length() <= 15;
    }
    public boolean isValidNumero(int numero) {
        return String.valueOf(numero).matches("^\\d{8}$");
    }
    public boolean isValidCin(int cin) {
        return String.valueOf(cin).matches("^\\d{8}$");
    }
    public boolean isValidAdresse(String adresse) {
        return adresse != null && !adresse.isEmpty();
    }
    public boolean isValidRole(String role) {
        return role != null && !role.isEmpty();
    }
    public boolean isValidEmail(String email) {
        return email != null && email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }
    public boolean isValidPassword(String password) {
        return password != null && password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }
}

