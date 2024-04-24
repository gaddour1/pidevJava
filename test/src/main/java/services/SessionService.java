package services;

public class SessionService {
    private static SessionService instance;
    private int signedInUserId = -1; // Initialisez avec un ID d'utilisateur invalide

    private SessionService() {}

    // Méthode pour obtenir l'instance unique de la classe
    public static synchronized SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    // Méthode pour définir l'ID de l'utilisateur connecté
    public void setSignedInUserId(int userId) {
        this.signedInUserId = userId;
    }

    // Méthode pour récupérer l'ID de l'utilisateur connecté
    public int getSignedInUserId() {
        return signedInUserId;
    }

    // Méthode pour effacer les données de la session
    public void clearSession() {
        signedInUserId = -1;
    }
}
