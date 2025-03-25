import com.mysql.cj.Session;
import db_api.BookDB;
import db_api.UserDB;
import utilities.GetBook;
import objects.Book;
import utilities.SessionManager;
import objects.User;

import java.util.List;


public class methodexamples {
    public static void main(String[] args) {

        //To create a book object you can import utilities.GetBook and use the static function
        //You have to use the isbn of the book, and it will automatically search for the title author etc.
        //You could theoretically just make a book manually, but I think we should always create books with the function
        Book test = GetBook.getBookByISBN("9780441172718");

        //You can interact with the Book Database by using the functions from db_api.BookDB;

        //to add a book to the database use the use addBook() with the book as the argument
        //it will then be added to the database, each book has a unique id so there can be multiple copies
        //of the same book
        BookDB.addBook(test);


        //You can search for books in the database using a variety of functions
        //They return a List of book objects of all the books that fit the descriptions

        List isbn = BookDB.searchByIsbn("9780441172719");
        System.out.println(isbn);
        List authors = BookDB.searchByAuthor("Frank Herbert");
        System.out.println(authors);
        List publishers = BookDB.searchByPublisher("Penguin");
        System.out.println(publishers);
        List title = BookDB.searchByTitle("Dune");
        System.out.println(title);

        //You can also search by id to return a single book object
        Book id = BookDB.getBookByID(4);
        System.out.println(id);

        //You can also get all books in the library as a List
        System.out.println(BookDB.getAllBooks());

        //You can also remove a book with the function deleteBook(int bookID)


        //To create a user you can import db_api.BookDB and use registerUser(String username, String password)

//        UserDB.registerUser("test3","password");

        //To log in just use loginUser(String username, String password)

        UserDB.loginUser("test","password");

        //Now the currently logged in user will be stored in SessionManager
        // which can be imported from utilities.SessionManager
        //the function will return a User object
        User loggedin = SessionManager.getCurrentUser();
        System.out.println(loggedin);

        //User objects have many functions such as returning the list of books they have checked out
        System.out.println(loggedin.getBooks());

        //You can also checkoutBooks to a user, doesn't have to be logged in
        //title doesn't have to be exact match

        loggedin.checkoutBook("Hobbit");
        System.out.println(loggedin.getBooks());
        //You can also return books, again user doesn't have to be logged in
        //the title must be an exact match

        loggedin.returnBook("The Hobbit, Or, There and Back Again");
        System.out.println(loggedin.getBooks());

        //after you're done you can log out the current user
        SessionManager.logoutUser();

    }
}
