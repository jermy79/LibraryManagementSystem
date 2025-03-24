package objects;

import java.util.List;
import java.util.stream.Collectors;

public class User {
    private String userName;
    private final String passwordHash;
    private List <Book> books;
    int userID;


    public User(String userName, String passwordHash) {
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public User(int userID,String userName, String passwordHash, List<Book> books) {
        this.userName = userName;
        this.passwordHash = passwordHash;
        this.books = books;
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public List <Book> getBooks() {
        return books;
    }

    @Override
    public String toString() {
        String booksString = (books == null || books.isEmpty())
                ? "No books checked out"
                : books.stream()
                .map(Book::getTitle)
                .collect(Collectors.joining(", "));

        return "{" +
                "userID=" + userID +
                ", userName='" + userName + '\'' +
                ", books=" + booksString +
                '}';
    }

}
