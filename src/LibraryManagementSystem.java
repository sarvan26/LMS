import book.Book;
import book.BookService;
import lending.LendingService;
import branch.Branch;
import branch.BranchService;
import notification.PatronNotifier;
import notification.ReservationSystem;
import patron.Patron;
import patron.PatronService;

import java.util.logging.Logger;

// Main Library Management System class that ties everything together
public class LibraryManagementSystem {
    private BookService bookService;
    private PatronService patronService;
    private LendingService lendingService;
    private ReservationSystem reservationSystem;
    private BranchService branchService;
    private static final Logger logger = Logger.getLogger(LibraryManagementSystem.class.getName());

    public LibraryManagementSystem() {
        this.bookService = new BookService();
        this.patronService = new PatronService();
        this.reservationSystem = new ReservationSystem();
        this.branchService = new BranchService();
        this.lendingService = new LendingService(bookService, patronService, reservationSystem);

        // Set up notification observer
        PatronNotifier notifier = new PatronNotifier();
        reservationSystem.addObserver(notifier);
    }

    // Example usage of the library system
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();

        // Create branches
        Branch mainBranch = new Branch("B001", "Main Library", "123 Main St");
        Branch downtownBranch = new Branch("B002", "Downtown Branch", "456 Downtown Ave");

        library.branchService.addBranch(mainBranch);
        library.branchService.addBranch(downtownBranch);

        // Add books to branches
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", "978-0743273565", 1925);
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", "978-0061120084", 1960);
        Book book3 = new Book("Encyclopedia Britannica", "Multiple Authors", "978-1593392925", 2010);

        library.bookService.addBook(book1, mainBranch);
        library.bookService.addBook(book2, mainBranch);
        library.bookService.addBook(book3, downtownBranch);

        // Add patrons
        Patron patron1 = new Patron("P001", "Saravanan", "Saravanan@gmail.com");
        Patron patron2 = new Patron("P002", "Jane", "jane@gmail.com");

        library.patronService.addPatron(patron1);
        library.patronService.addPatron(patron2);

        // Checkout a book
        library.lendingService.checkoutBook("978-0743273565", "P001");

        // Reserve a book
        library.reservationSystem.reserveBook("978-0061120084", patron2);

        // Return a book
        library.lendingService.returnBook("978-0743273565", "P001");

        // Show available books
        System.out.println("Available books:");
        for (Book book : library.bookService.getAvailableBooks()) {
            System.out.println(book);
        }

        // Transfer a book between branches
        library.branchService.transferBook("978-0743273565", "B001", "B002");

        // Show books by branch
        System.out.println("Books in Downtown branch:");
        for (Book book : library.bookService.getBooksByBranch(downtownBranch)) {
            System.out.println(book);
        }
    }
}
