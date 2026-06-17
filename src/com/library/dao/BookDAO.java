package com.library.dao;

import com.library.model.Book;
import com.library.util.DBConnection;

import java.sql.*;

public class BookDAO {

    public void addBook(Book book) {
        String query = "INSERT INTO books(title, author, category, quantity) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getQuantity());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book added successfully.");
            }

        } catch (Exception e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public void viewAllBooks() {
        String query = "SELECT * FROM books";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nID | Title | Author | Category | Quantity");
            System.out.println("--------------------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("book_id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("author") + " | " +
                        rs.getString("category") + " | " +
                        rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            System.out.println("Error viewing books: " + e.getMessage());
        }
    }

    public void searchBookById(int bookId) {
        String query = "SELECT * FROM books WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("Book ID: " + rs.getInt("book_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Category: " + rs.getString("category"));
                System.out.println("Quantity: " + rs.getInt("quantity"));
            } else {
                System.out.println("Book not found.");
            }

        } catch (Exception e) {
            System.out.println("Error searching book: " + e.getMessage());
        }
    }

    public void updateBookQuantity(int bookId, int quantity) {
        String query = "UPDATE books SET quantity = ? WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, quantity);
            ps.setInt(2, bookId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book quantity updated successfully.");
            } else {
                System.out.println("Book not found.");
            }

        } catch (Exception e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook(int bookId) {
        String query = "DELETE FROM books WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookId);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println("Book not found.");
            }

        } catch (Exception e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }
}
