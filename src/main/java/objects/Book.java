package objects;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int isbn;
    private int checkoutDate;
    private int dueDate;
    private boolean checkedOut;

    public Book(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\nAuthor: " + author + "\nPublisher: " + publisher;
    }
}