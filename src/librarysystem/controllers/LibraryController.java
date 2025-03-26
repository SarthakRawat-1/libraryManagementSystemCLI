/**
 * This class is responsible for processing user input from the view,
 * manipulating the model data, and updating the view accordingly.
 */
package librarysystem.controllers;

import librarysystem.models.Book;
import librarysystem.models.Member;
import librarysystem.models.BookLoan;
import librarysystem.views.LibraryView;

import java.util.*;
import java.time.LocalDate;

public class LibraryController {
    private List<Book> bookCollection;
    private List<Member> memberList;
    private List<BookLoan> loanRecords;
    private LibraryView view;
    private Scanner scanner;

    public LibraryController(LibraryView view) {
        this.bookCollection = new ArrayList<>();
        this.memberList = new ArrayList<>();
        this.loanRecords = new ArrayList<>();
        this.view = view;
        this.scanner = new Scanner(System.in);
    }


    public void addSampleData() {
        bookCollection.add(new Book("1001", "The Fall of the House of Usher and Other Writings", "Edgar Allan Poe", "Fiction", 2003));
        bookCollection.add(new Book("1002", "Data Communications and Networking", "Behrouz A. Forouzan", "Technical", 2006));
        bookCollection.add(new Book("1003", "War and Peace", "Leo Tolstoy", "Historical Fiction", 1867));
        bookCollection.add(new Book("1004", "Operating System Concepts", "Abraham Silberschatz", "Technical", 1998));
        bookCollection.add(new Book("1005", "War! What Is It Good For?", "Ian Morris", "History", 2014));

        // Add sample members
        memberList.add(new Member("M001", "Sarthak Rawat", "sarthakrawat525@gmail.com", "123-1234-123"));
        memberList.add(new Member("M002", "Shogun", "shogun@gmail.com", "555-5555-555"));
        memberList.add(new Member("M003", "Sample User", "sample@gmail.com", "987-9876-987"));
    }

    /**
     * Main menu loop for the application
     */
    public void runMenu() {
        boolean exit = false;

        while (!exit) {
            view.displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    bookManagementMenu();
                    break;
                case 2:
                    memberManagementMenu();
                    break;
                case 3:
                    loanManagementMenu();
                    break;
                case 4:
                    searchMenu();
                    break;
                case 5:
                    exit = true;
                    break;
                default:
                    view.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Menu for managing books
     */
    private void bookManagementMenu() {
        boolean back = false;

        while (!back) {
            view.displayBookManagementMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    removeBook();
                    break;
                case 3:
                    listAllBooks();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    view.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Menu for managing members
     */
    private void memberManagementMenu() {
        boolean back = false;

        while (!back) {
            view.displayMemberManagementMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    addMember();
                    break;
                case 2:
                    removeMember();
                    break;
                case 3:
                    listAllMembers();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    view.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Menu for managing loans
     */
    private void loanManagementMenu() {
        boolean back = false;

        while (!back) {
            view.displayLoanManagementMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    issueBook();
                    break;
                case 2:
                    returnBook();
                    break;
                case 3:
                    listAllLoans();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    view.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Menu for searching the library
     */
    private void searchMenu() {
        boolean back = false;

        while (!back) {
            view.displaySearchMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    searchBookByTitle();
                    break;
                case 2:
                    searchBookByAuthor();
                    break;
                case 3:
                    searchMemberById();
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    view.displayMessage("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Add a new book to the collection
     */
    private void addBook() {
        view.displayMessage("\n=== Add New Book ===");

        String isbn = getInput("Enter ISBN: ");

        // Check if book with ISBN already exists
        if (findBookByISBN(isbn) != null) {
            view.displayMessage("A book with this ISBN already exists!");
            return;
        }

        String title = getInput("Enter Title: ");
        String author = getInput("Enter Author: ");
        String category = getInput("Enter Category: ");
        int year = getIntInput("Enter Publication Year: ");

        Book newBook = new Book(isbn, title, author, category, year);
        bookCollection.add(newBook);

        view.displayMessage("Book added successfully!");
    }

    /**
     * Remove a book from the collection
     */
    private void removeBook() {
        view.displayMessage("\n=== Remove Book ===");
        String isbn = getInput("Enter ISBN of book to remove: ");

        Book bookToRemove = findBookByISBN(isbn);
        if (bookToRemove == null) {
            view.displayMessage("Book not found!");
            return;
        }

        // Check if book is currently loaned out
        for (BookLoan loan : loanRecords) {
            if (loan.getBook().getIsbn().equals(isbn) && loan.getReturnDate() == null) {
                view.displayMessage("Cannot remove book - it is currently loaned out!");
                return;
            }
        }

        bookCollection.remove(bookToRemove);
        view.displayMessage("Book removed successfully!");
    }

    /**
     * Display all books in the collection
     */
    private void listAllBooks() {
        view.displayMessage("\n=== All Books ===");

        if (bookCollection.isEmpty()) {
            view.displayMessage("No books in the library.");
            return;
        }

        // Sort books by title
        List<Book> sortedBooks = new ArrayList<>(bookCollection);
        Collections.sort(sortedBooks);

        view.displayBooks(sortedBooks);
    }

    /**
     * Add a new member
     */
    private void addMember() {
        view.displayMessage("\n=== Add New Member ===");

        String id = getInput("Enter Member ID: ");

        // Check if member with ID already exists
        if (findMemberById(id) != null) {
            view.displayMessage("A member with this ID already exists!");
            return;
        }

        String name = getInput("Enter Name: ");
        String email = getInput("Enter Email: ");
        String phone = getInput("Enter Phone: ");

        Member newMember = new Member(id, name, email, phone);
        memberList.add(newMember);

        view.displayMessage("Member added successfully!");
    }

    /**
     * Remove a member from the system
     */
    private void removeMember() {
        view.displayMessage("\n=== Remove Member ===");
        String id = getInput("Enter ID of member to remove: ");

        Member memberToRemove = findMemberById(id);
        if (memberToRemove == null) {
            view.displayMessage("Member not found!");
            return;
        }

        // Check if member has any active loans
        for (BookLoan loan : loanRecords) {
            if (loan.getMember().getId().equals(id) && loan.getReturnDate() == null) {
                view.displayMessage("Cannot remove member - they have books checked out!");
                return;
            }
        }

        memberList.remove(memberToRemove);
        view.displayMessage("Member removed successfully!");
    }

    /**
     * Display all members
     */
    private void listAllMembers() {
        view.displayMessage("\n=== All Members ===");

        if (memberList.isEmpty()) {
            view.displayMessage("No members registered.");
            return;
        }

        view.displayMembers(memberList);
    }

    /**
     * Issue a book to a member
     */
    private void issueBook() {
        view.displayMessage("\n=== Issue Book ===");

        String memberId = getInput("Enter Member ID: ");
        Member member = findMemberById(memberId);

        if (member == null) {
            view.displayMessage("Member not found!");
            return;
        }

        String isbn = getInput("Enter Book ISBN: ");
        Book book = findBookByISBN(isbn);

        if (book == null) {
            view.displayMessage("Book not found!");
            return;
        }

        // Check if book is already loaned out
        for (BookLoan loan : loanRecords) {
            if (loan.getBook().getIsbn().equals(isbn) && loan.getReturnDate() == null) {
                view.displayMessage("This book is already checked out!");
                return;
            }
        }

        // Create new loan record
        BookLoan loan = new BookLoan(book, member, LocalDate.now());
        loanRecords.add(loan);

        view.displayMessage("Book issued successfully!");
    }

    /**
     * Process a book return
     */
    private void returnBook() {
        view.displayMessage("\n=== Return Book ===");

        String isbn = getInput("Enter Book ISBN: ");

        // Find the active loan for this book
        BookLoan loanToReturn = null;
        for (BookLoan loan : loanRecords) {
            if (loan.getBook().getIsbn().equals(isbn) && loan.getReturnDate() == null) {
                loanToReturn = loan;
                break;
            }
        }

        if (loanToReturn == null) {
            view.displayMessage("No active loan found for this book!");
            return;
        }

        // Process the return
        loanToReturn.setReturnDate(LocalDate.now());
        view.displayMessage("Book returned successfully!");
    }

    /**
     * Display all active loans
     */
    private void listAllLoans() {
        view.displayMessage("\n=== All Current Loans ===");

        List<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan loan : loanRecords) {
            if (loan.getReturnDate() == null) {
                activeLoans.add(loan);
            }
        }

        if (activeLoans.isEmpty()) {
            view.displayMessage("No active loans.");
            return;
        }

        view.displayLoans(activeLoans);
    }

    /**
     * Search for a book by title
     */
    private void searchBookByTitle() {
        view.displayMessage("\n=== Search Book by Title ===");

        String title = getInput("Enter title to search for: ").toLowerCase();

        List<Book> foundBooks = new ArrayList<>();
        for (Book book : bookCollection) {
            if (book.getTitle().toLowerCase().contains(title)) {
                foundBooks.add(book);
            }
        }

        if (foundBooks.isEmpty()) {
            view.displayMessage("No books found matching that title.");
            return;
        }

        view.displayBooks(foundBooks);
    }

    /**
     * Search for books by a particular author
     */
    private void searchBookByAuthor() {
        view.displayMessage("\n=== Search Book by Author ===");

        String author = getInput("Enter author to search for: ").toLowerCase();

        List<Book> foundBooks = new ArrayList<>();
        for (Book book : bookCollection) {
            if (book.getAuthor().toLowerCase().contains(author)) {
                foundBooks.add(book);
            }
        }

        if (foundBooks.isEmpty()) {
            view.displayMessage("No books found by that author.");
            return;
        }

        view.displayBooks(foundBooks);
    }

    /**
     * Search for a member by ID
     */
    private void searchMemberById() {
        view.displayMessage("\n=== Search Member by ID ===");

        String id = getInput("Enter member ID: ");
        Member member = findMemberById(id);

        if (member == null) {
            view.displayMessage("Member not found!");
            return;
        }

        view.displayMember(member);

        // Display any active loans for this member
        List<BookLoan> activeLoans = new ArrayList<>();
        for (BookLoan loan : loanRecords) {
            if (loan.getMember().getId().equals(id) && loan.getReturnDate() == null) {
                activeLoans.add(loan);
            }
        }

        if (!activeLoans.isEmpty()) {
            view.displayMessage("\nActive Loans:");
            view.displayLoans(activeLoans);
        }
    }

    private Book findBookByISBN(String isbn) {
        for (Book book : bookCollection) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    }

    private Member findMemberById(String id) {
        for (Member member : memberList) {
            if (member.getId().equals(id)) {
                return member;
            }
        }
        return null;
    }

    /**
     * Get text input from user with prompt
     */
    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    /**
     * Get integer input from user with validation
     */
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                // nextInt() leaves \n in Buffer and also throws InputMismatchException
                // for non numeric output while keeping the invalid input in scanner.
                // So we are using nextLine()
                int input = Integer.parseInt(scanner.nextLine().trim());
                return input;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}