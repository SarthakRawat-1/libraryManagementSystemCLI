/**
 * This class is responsible for displaying information to the user
 * and formatting data for presentation.
 */
package librarysystem.views;

import librarysystem.models.Book;
import librarysystem.models.Member;
import librarysystem.models.BookLoan;

import java.util.List;
import java.time.format.DateTimeFormatter;

public class LibraryView {
    private final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public void displayMainMenu() {
        System.out.println("\n=== Library Management System ===");
        System.out.println("1. Book Management");
        System.out.println("2. Member Management");
        System.out.println("3. Loan Management");
        System.out.println("4. Search");
        System.out.println("5. Exit");
    }

    public void displayBookManagementMenu() {
        System.out.println("\n=== Book Management ===");
        System.out.println("1. Add New Book");
        System.out.println("2. Remove Book");
        System.out.println("3. List All Books");
        System.out.println("4. Back to Main Menu");
    }

    public void displayMemberManagementMenu() {
        System.out.println("\n=== Member Management ===");
        System.out.println("1. Register New Member");
        System.out.println("2. Remove Member");
        System.out.println("3. List All Members");
        System.out.println("4. Back to Main Menu");
    }

    public void displayLoanManagementMenu() {
        System.out.println("\n=== Loan Management ===");
        System.out.println("1. Issue Book");
        System.out.println("2. Return Book");
        System.out.println("3. View All Current Loans");
        System.out.println("4. Back to Main Menu");
    }

    public void displaySearchMenu() {
        System.out.println("\n=== Search ===");
        System.out.println("1. Search Book by Title");
        System.out.println("2. Search Book by Author");
        System.out.println("3. Search Member by ID");
        System.out.println("4. Back to Main Menu");
    }

    public void displayBooks(List<Book> books) {
        System.out.println("\nBooks:");
        System.out.printf("%-10s %-30s %-20s %-15s %-6s%n",
                "ISBN", "Title", "Author", "Category", "Year");
        System.out.println("-------------------------------------------------------------------------------------");

        for (Book book : books) {
            System.out.printf("%-10s %-30s %-20s %-15s %-6d%n",
                    book.getIsbn(),
                    truncateString(book.getTitle(), 28),
                    truncateString(book.getAuthor(), 18),
                    truncateString(book.getCategory(), 13),
                    book.getPublicationYear());
        }
    }

    public void displayBook(Book book) {
        System.out.println("\nBook Details:");
        System.out.println("ISBN: " + book.getIsbn());
        System.out.println("Title: " + book.getTitle());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Category: " + book.getCategory());
        System.out.println("Publication Year: " + book.getPublicationYear());
    }

    public void displayMembers(List<Member> members) {
        System.out.println("\nMembers:");
        System.out.printf("%-10s %-20s %-25s %-15s%n",
                "ID", "Name", "Email", "Phone");
        System.out.println("------------------------------------------------------------------------");

        for (Member member : members) {
            System.out.printf("%-10s %-20s %-25s %-15s%n",
                    member.getId(),
                    truncateString(member.getName(), 18),
                    truncateString(member.getEmail(), 23),
                    member.getPhone());
        }
    }

    public void displayMember(Member member) {
        System.out.println("\nMember Details:");
        System.out.println("ID: " + member.getId());
        System.out.println("Name: " + member.getName());
        System.out.println("Email: " + member.getEmail());
        System.out.println("Phone: " + member.getPhone());
    }

    public void displayLoans(List<BookLoan> loans) {
        System.out.println("\nLoans:");
        System.out.printf("%-10s %-30s %-20s %-12s %-12s%n",
                "Book ISBN", "Book Title", "Member", "Issue Date", "Return Date");
        System.out.println("-------------------------------------------------------------------------------------");

        for (BookLoan loan : loans) {
            String returnDate = loan.getReturnDate() == null ? "Not returned" :
                    loan.getReturnDate().format(dateFormat);

            System.out.printf("%-10s %-30s %-20s %-12s %-12s%n",
                    loan.getBook().getIsbn(),
                    truncateString(loan.getBook().getTitle(), 28),
                    truncateString(loan.getMember().getName(), 18),
                    loan.getIssueDate().format(dateFormat),
                    returnDate);
        }
    }

    public void displayMessage(String message) {
        System.out.println(message);
    }

    /**
     * Helper method to truncate strings for display formatting
     */
    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}