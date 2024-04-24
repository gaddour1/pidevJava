package org.example.amine.services;

public class serviceUser {
    private static serviceUser instance;
    private static int userId;

    private serviceUser() {}

    public static serviceUser getInstance() {
        if (instance == null) {
            instance = new serviceUser();
        }
        return instance;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public static int getUserId() {
        return userId;
    }
}
