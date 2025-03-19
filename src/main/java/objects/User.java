package objects;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private int userID;
    private String userName;
    private String passwordHash;
    private Book[] books;

    public User(int userID, String userName, String password) {
        this.userID = userID;
        this.userName = userName;
        this.passwordHash = this.hashPassword(password);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    public int getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public boolean checkPassword(String password) {
        return BCrypt.checkpw(password, this.passwordHash);
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
