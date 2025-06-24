package lending;

import book.Book;
import book.BookService;
import notification.ReservationSystem;
import patron.Patron;
import patron.PatronService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LendingService {
    private List<LendingRecord> lendingRecords;
    private BookService bookService;
    private PatronService patronService;
    private ReservationSystem reservationSystem;
    private static final Logger logger = Logger.getLogger(LendingService.class.getName());

    public LendingService(BookService bookService, PatronService patronService, ReservationSystem reservationSystem) {
        this.lendingRecords = new ArrayList<>();
        this.bookService = bookService;
        this.patronService = patronService;
        this.reservationSystem = reservationSystem;
    }

    public boolean checkoutBook(String isbn, String patronId) {
        Book book = bookService.findBookByIsbn(isbn);
        Patron patron = patronService.findPatronById(patronId);

        if (book == null || patron == null) {
            logger.warning("Book or patron not found.");
            return false;
        }

        if (!book.isAvailable()) {
            logger.warning("Book " + book.getTitle() + " is not available.");
            return false;
        }

        // Check if the book is reserved for someone else
        List<Patron> reservations = reservationSystem.getReservations(isbn);
        if (!reservations.isEmpty() && !reservations.get(0).equals(patron)) {
            logger.warning("Book is reserved for another patron.");
            return false;
        }

        LendingRecord record = new LendingRecord(book, patron);
        lendingRecords.add(record);
        patron.addBorrowingRecord(record);
        book.setAvailable(false);

        // Remove reservation if existed
        if (!reservations.isEmpty() && reservations.get(0).equals(patron)) {
            reservations.remove(0);
        }

        logger.info("Book " + book.getTitle() + " checked out by " + patron.getName());
        return true;
    }

    public boolean returnBook(String isbn, String patronId) {
        Book book = bookService.findBookByIsbn(isbn);
        Patron patron = patronService.findPatronById(patronId);

        if (book == null || patron == null) {
            logger.warning("Book or patron not found.");
            return false;
        }

        Optional<LendingRecord> record = lendingRecords.stream()
                .filter(r -> r.getBook().getIsbn().equals(isbn) &&
                        r.getPatron().getId().equals(patronId) &&
                        !r.isReturned())
                .findFirst();

        if (record.isPresent()) {
            record.get().setReturnDate(LocalDate.now());
            book.setAvailable(true);

            // Notify next patron in reservation queue
            reservationSystem.notifyBookAvailable(book);

            logger.info("Book " + book.getTitle() + " returned by " + patron.getName());
            return true;
        }

        logger.warning("No active lending record found for this book and patron.");
        return false;
    }

    public List<LendingRecord> getActiveLendings() {
        return lendingRecords.stream()
                .filter(record -> !record.isReturned())
                .collect(Collectors.toList());
    }

    public List<LendingRecord> getOverdueBooks() {
        return lendingRecords.stream()
                .filter(record -> !record.isReturned() && record.isOverdue())
                .collect(Collectors.toList());
    }

    public List<LendingRecord> getPatronBorrowingHistory(String patronId) {
        return lendingRecords.stream()
                .filter(record -> record.getPatron().getId().equals(patronId))
                .collect(Collectors.toList());
    }
}
