package lending;

import book.Book;
import patron.Patron;

import java.time.LocalDate;

public class LendingRecord {
    private Book book;
    private Patron patron;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    public LendingRecord(Book book, Patron patron) {
        this.book = book;
        this.patron = patron;
        this.checkoutDate = LocalDate.now();
        this.dueDate = checkoutDate.plusDays(14); // 2 weeks lending period
        this.returnDate = null;
    }

    // Getters and setters
    public Book getBook() { return book; }
    public Patron getPatron() { return patron; }
    public LocalDate getCheckoutDate() { return checkoutDate; }
    public LocalDate getDueDate() { return dueDate; }
    public LocalDate getReturnDate() { return returnDate; }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returnDate != null;
    }

    public boolean isOverdue() {
        return !isReturned() && LocalDate.now().isAfter(dueDate);
    }

    @Override
    public String toString() {
        return "lending.LendingRecord{" +
                "book=" + book.getTitle() +
                ", patron=" + patron.getName() +
                ", checkoutDate=" + checkoutDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
