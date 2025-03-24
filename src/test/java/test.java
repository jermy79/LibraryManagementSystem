import db_api.BookDB;
import db_api.UserDB;
import objects.User;
import utilities.GetBook;
import objects.Book;
import utilities.SessionManager;

import java.util.List;


public class test {
    public static void main(String[] args) {
        User test = UserDB.getUserByUsername("test2");

        UserDB.loginUser("test","password");

        SessionManager.getCurrentUser().getBooks();
        System.out.println(SessionManager.getCurrentUser().getBooks());


    }
}
