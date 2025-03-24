import db_api.BookDB;
import utilities.GetBook;
import objects.Book;
import utilities.SessionManager;

import java.util.List;


public class test {
    public static void main(String[] args) {
        Book test = GetBook.getBookByISBN("0316327336");

        System.out.println(BookDB.getAllBooks());
    }
}
