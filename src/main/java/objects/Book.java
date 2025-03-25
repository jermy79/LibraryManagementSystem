package objects;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private int bookID = -1;
    private String checkoutDate;
    private String dueDate;
    private boolean checkedOut;

    public Book(String title, String author, String publisher, String isbn) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
    }

    public Book(int bookID,String title, String author, String publisher, String isbn, boolean checkedOut) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.bookID = bookID;
        this.checkedOut = checkedOut;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }
    public Book(int bookID,String title, String author, String publisher, String isbn, boolean checkedOut,String checkoutDate,String dueDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.bookID = bookID;
        this.checkedOut = checkedOut;
        this.checkoutDate = checkoutDate;
        this.dueDate = dueDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getIsbn(){
        return isbn;
    }

    public int getBookID () {
        return bookID;
    }

    public String getCheckoutDate() {
        return checkoutDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public boolean isCheckedOut() {
        return checkedOut;
    }

    @Override
    public String toString() {
        return "Book ID: " + bookID + "\nISBN: " + isbn + "\nTitle: " + title + "\nAuthor: " + author + "\nPublisher: " + publisher + "\nChecked Out?: " + checkedOut;
    }
}