package com.library.main;

import com.library.model.Book;
import com.library.model.Member;
import com.library.service.LibraryService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LibraryService service = new LibraryService();

        while (true) {
            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. View All Books");
            System.out.println("3. Search Book by ID");
            System.out.println("4. Update Book Quantity");
            System.out.println("5. Delete Book");
            System.out.println("6. Add Member");
            System.out.println("7. View All Members");
            System.out.println("8. Issue Book");
            System.out.println("9. Return Book");
            System.out.println("10. View Issued Books");
            System.out.println("11. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.print("Enter book title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter author name: ");
                    String author = sc.nextLine();

                    System.out.print("Enter category: ");
                    String category = sc.nextLine();

                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();

                    Book book = new Book(title, author, category, quantity);
                    service.addBook(book);
                    break;

                case 2:
                    service.viewAllBooks();
                    break;

                case 3:
                    System.out.print("Enter book ID: ");
                    int searchId = sc.nextInt();
                    service.searchBookById(searchId);
                    break;

                case 4:
                    System.out.print("Enter book ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter new quantity: ");
                    int newQuantity = sc.nextInt();

                    service.updateBookQuantity(updateId, newQuantity);
                    break;

                case 5:
                    System.out.print("Enter book ID: ");
                    int deleteId = sc.nextInt();
                    service.deleteBook(deleteId);
                    break;

                case 6:
                    System.out.print("Enter member name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter phone: ");
                    String phone = sc.nextLine();

                    Member member = new Member(name, email, phone);
                    service.addMember(member);
                    break;

                case 7:
                    service.viewAllMembers();
                    break;

                case 8:
                    System.out.print("Enter book ID: ");
                    int bookId = sc.nextInt();

                    System.out.print("Enter member ID: ");
                    int memberId = sc.nextInt();

                    service.issueBook(bookId, memberId);
                    break;

                case 9:
                    System.out.print("Enter issue ID: ");
                    int issueId = sc.nextInt();

                    service.returnBook(issueId);
                    break;

                case 10:
                    service.viewIssuedBooks();
                    break;

                case 11:
                    System.out.println("Thank you for using Library Management System.");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
