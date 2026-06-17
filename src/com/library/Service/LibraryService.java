package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.MemberDAO;
import com.library.model.Book;
import com.library.model.Member;
import com.library.util.DBConnection;

import java.sql.*;

public class LibraryService {

    private BookDAO bookDAO = new BookDAO();
    private MemberDAO memberDAO = new MemberDAO();

    public void addBook(Book book) {
        bookDAO.addBook(book);
    }

    public void viewAllBooks() {
        bookDAO.viewAllBooks();
    }

    public void searchBookById(int bookId) {
        bookDAO.searchBookById(bookId);
    }

    public void updateBookQuantity(int bookId, int quantity) {
        bookDAO.updateBookQuantity(bookId, quantity);
    }

    public void deleteBook(int bookId) {
        bookDAO.deleteBook(bookId);
    }

    public void addMember(Member member) {
        memberDAO.addMember(member);
    }

    public void viewAllMembers() {
        memberDAO.viewAllMembers();
    }

    public void issueBook(int bookId, int memberId) {
        String checkBookQuery = "SELECT quantity FROM books WHERE book_id = ?";
        String issueQuery = "INSERT INTO issued_books(book_id, member_id, issue_date, status) VALUES (?, ?, CURDATE(), ?)";
        String updateBookQuery = "UPDATE books SET quantity = quantity - 1 WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement checkPs = con.prepareStatement(checkBookQuery);
            checkPs.setInt(1, bookId);
            ResultSet rs = checkPs.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");

                if (quantity > 0) {
                    PreparedStatement issuePs = con.prepareStatement(issueQuery);
                    issuePs.setInt(1, bookId);
                    issuePs.setInt(2, memberId);
                    issuePs.setString(3, "Issued");
                    issuePs.executeUpdate();

                    PreparedStatement updatePs = con.prepareStatement(updateBookQuery);
                    updatePs.setInt(1, bookId);
                    updatePs.executeUpdate();

                    System.out.println("Book issued successfully.");
                } else {
                    System.out.println("Book is not available.");
                }
            } else {
                System.out.println("Book not found.");
            }

        } catch (Exception e) {
            System.out.println("Error issuing book: " + e.getMessage());
        }
    }

    public void returnBook(int issueId) {
        String getBookQuery = "SELECT book_id FROM issued_books WHERE issue_id = ? AND status = 'Issued'";
        String returnQuery = "UPDATE issued_books SET return_date = CURDATE(), status = 'Returned' WHERE issue_id = ?";
        String updateBookQuery = "UPDATE books SET quantity = quantity + 1 WHERE book_id = ?";

        try (Connection con = DBConnection.getConnection()) {

            PreparedStatement getPs = con.prepareStatement(getBookQuery);
            getPs.setInt(1, issueId);
            ResultSet rs = getPs.executeQuery();

            if (rs.next()) {
                int bookId = rs.getInt("book_id");

                PreparedStatement returnPs = con.prepareStatement(returnQuery);
                returnPs.setInt(1, issueId);
                returnPs.executeUpdate();

                PreparedStatement updatePs = con.prepareStatement(updateBookQuery);
                updatePs.setInt(1, bookId);
                updatePs.executeUpdate();

                System.out.println("Book returned successfully.");
            } else {
                System.out.println("Issued record not found or book already returned.");
            }

        } catch (Exception e) {
            System.out.println("Error returning book: " + e.getMessage());
        }
    }

    public void viewIssuedBooks() {
        String query = """
                SELECT 
                    ib.issue_id,
                    b.title,
                    m.name,
                    ib.issue_date,
                    ib.return_date,
                    ib.status
                FROM issued_books ib
                JOIN books b ON ib.book_id = b.book_id
                JOIN members m ON ib.member_id = m.member_id
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            System.out.println("\nIssue ID | Book | Member | Issue Date | Return Date | Status");
            System.out.println("---------------------------------------------------------------");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("issue_id") + " | " +
                        rs.getString("title") + " | " +
                        rs.getString("name") + " | " +
                        rs.getDate("issue_date") + " | " +
                        rs.getDate("return_date") + " | " +
                        rs.getString("status")
                );
            }

        } catch (Exception e) {
            System.out.println("Error viewing issued books: " + e.getMessage());
        }
    }
}
