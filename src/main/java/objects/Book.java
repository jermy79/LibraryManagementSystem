package objects;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int checkoutDate;
    private int dueDate;
    private boolean checkedOut;

    public Book(String title, String author, String publisher, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn + "\nTitle: " + title + "\nAuthor: " + author + "\nPublisher: " + publisher;
    }
}