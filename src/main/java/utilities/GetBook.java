package utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import objects.Book;
import org.json.JSONArray;
import org.json.JSONObject;


public class GetBook {
    private static final String API_KEY = "AIzaSyB_7IXwfY2AmLbDH5j-aE9ohVQfzE6KNPg";
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=isbn:%s&key=%s";

    public static Book getBookByISBN(String isbn) {
        try {
            String urlString = String.format(API_URL, isbn, API_KEY);
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JSONObject json = new JSONObject(response.toString());
            JSONArray items = json.optJSONArray("items");

            if (items != null && items.length() > 0) {
                JSONObject volumeInfo = items.getJSONObject(0).getJSONObject("volumeInfo");

                String title = volumeInfo.optString("title", "Unknown Title");
                String author = "Unknown Author";
                String publisher = volumeInfo.optString("publisher", "Unknown Publisher");

                if (volumeInfo.has("authors")) {
                    JSONArray authorsArray = volumeInfo.getJSONArray("authors");
                    author = String.join(", ", authorsArray.toList().stream().map(Object::toString).toArray(String[]::new));
                }

                return new Book(title, author, publisher);
            }
        } catch (Exception e) {
            System.err.println("Error fetching book details: " + e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        String isbn = "9780134685991"; // Example ISBN
        Book book = getBookByISBN(isbn);

        if (book != null) {
            System.out.println(book);
        } else {
            System.out.println("objects.Book not found for ISBN: " + isbn);
        }
    }
}
