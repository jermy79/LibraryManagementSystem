package objects;

import java.util.List;
import java.util.stream.Collectors;

public class Admin extends User {

    private boolean isAdmin = true;


    public Admin(String username, String password) {
        super(username,password);
    }

    public Admin(int userID, String userName, String passwordHash, List<Book> userBooks) {
        super(userID,userName,passwordHash,userBooks);
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    @Override
    public String toString() {
        String booksString = (getBooks() == null || getBooks().isEmpty())
                ? "No books checked out"
                : getBooks().stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(", "));

        return "{" +
                "isAdmin=" + isAdmin +
                ", userID=" + userID +
                ", userName='" + getUserName() + '\'' +
                ", books=" + booksString +
                '}';
    }
}
