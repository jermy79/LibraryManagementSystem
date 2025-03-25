package objects;

import db_api.BookDB;
import db_api.UserDB;

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

    public int getUserID() {
        return userID;
    }

    public boolean checkoutBook(String bookTitle) {
        // Retrieve available books of the requested title that are not checked out
        List<Book> availableBooks = BookDB.getAvailableBooksByTitle(bookTitle);

        // If no available copies, return false (book not available)
        if (availableBooks.isEmpty()) {
            System.out.println("No available copies of the book: " + bookTitle);
            return false;
        }

        // Proceed to checkout the first available book
        Book bookToCheckout = availableBooks.get(0); // Checkout the first available book
        boolean isCheckedOut = UserDB.checkoutBook(this.userID, bookToCheckout.getBookID());
        List<Book> updatedBooks = UserDB.getUserBooks(this.userID);
        this.books = (updatedBooks);

        if (isCheckedOut) {
            // Update the books list of the current user after checkout
            this.books.remove(bookToCheckout);

        }

        return isCheckedOut;
    }

    public boolean returnBook(String bookTitle) {
        for (Book book : books) {
            if (book.getTitle().equals(bookTitle)) {
                // Return the book in the database (update the status)
                boolean success = UserDB.returnBook(book.getBookID(), this.userID);

                if (success) {
                    // Remove from the user's in-memory list
                    books.remove(book);
                    System.out.println("Book removed from user's list: " + bookTitle);

                    // Refresh the user's books list from the database
                    List<Book> updatedBooks = UserDB.getUserBooks(this.userID);
                    this.books = updatedBooks;  // Update the book list with fresh data

                    return true; // Book returned successfully
                }
            }
        }
        return false; // Book not found in the user's list
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
