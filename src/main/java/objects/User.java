package objects;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String userName;
    private String passwordHash;
    private Book[] books;

    public User(String userName, String password) {
        this.userName = userName;
        this.passwordHash = this.hashPassword(password);
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
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
