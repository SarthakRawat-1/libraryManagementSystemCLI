/**
 * This class tracks the lending of a book to a member,
 * including issue date and return date.
 */
package librarysystem.models;

import java.time.LocalDate;
import java.util.Objects;

public class BookLoan {
    private Book book;
    private Member member;
    private LocalDate issueDate;
    private LocalDate returnDate;

    public BookLoan(Book book, Member member, LocalDate issueDate) {
        this.book = book;
        this.member = member;
        this.issueDate = issueDate;
        this.returnDate = null; // Initially not returned
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }


    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Check if the book is currently checked out
     * True if checked out, false if returned
     */
    public boolean isActive() {
        return returnDate == null;
    }

    /**
     * Calculate the loan duration in days
     */
    public long calculateLoanDuration() {
        if (returnDate == null) {
            return LocalDate.now().toEpochDay() - issueDate.toEpochDay();
        } else {
            return returnDate.toEpochDay() - issueDate.toEpochDay();
        }
    }

    /**
     * Equals method to compare loans
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookLoan bookLoan = (BookLoan) o; // Necessary, otherwise o is still Object at compile time.
        return Objects.equals(book.getIsbn(), bookLoan.book.getIsbn()) &&
                Objects.equals(member.getId(), bookLoan.member.getId()) &&
                Objects.equals(issueDate, bookLoan.issueDate);
    }

    /**
     * Generate hash code for the loan
     */
    @Override
    public int hashCode() {
        return Objects.hash(book.getIsbn(), member.getId(), issueDate);
    }

    @Override
    public String toString() {
        return "BookLoan{" +
                "book=" + book.getTitle() +
                ", member=" + member.getName() +
                ", issueDate=" + issueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}