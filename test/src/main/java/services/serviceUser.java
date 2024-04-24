package services;
public class serviceUser {
    private static serviceUser instance;
    private static int signedInUserId; // Store the signed-in user ID

    private serviceUser() {}

    public static serviceUser getInstance() {
        if (instance == null) {
            instance = new serviceUser();
        }
        return instance;
    }

    // Method to set the signed-in user ID
    public void setSignedInUserId(int userId) {
        this.signedInUserId = userId;
    }

    // Method to get the signed-in user ID
    public static int getSignedInUserId() {
        return signedInUserId;
    }
}